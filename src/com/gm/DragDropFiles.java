package com.gm;

import java.awt.Component;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

class DragDropFiles extends DropTargetAdapter {

    private List<DnDFileListener> ddls = new LinkedList<DnDFileListener>();

    public DragDropFiles(DnDFileListener dl) {
        ddls.add(dl);
    }

    public static void add(Component c, DnDFileListener dl) {
        new DropTarget(c, new DragDropFiles(dl));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void drop(DropTargetDropEvent event) {
        event.acceptDrop(DnDConstants.ACTION_COPY);// Accept copy drops
        var transferable = event.getTransferable();
        for (var flavor : transferable.getTransferDataFlavors())
            if (flavor.isFlavorJavaFileListType())
                try {
                    for (var file : (List<File>) transferable.getTransferData(flavor))
                        if (file.isFile())
                            ddls.forEach(d -> d.notify(file));
                } catch (Exception e) {// unreachable?
                }
        event.dropComplete(true);// Inform that the drop is complete
    }
}