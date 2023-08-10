package com.gm;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public final class MenuUtils {
    // Suppresses default constructor, ensuring non-instantiability.
    private MenuUtils() {
        throw new UnsupportedOperationException("No " + this.getClass() + " instances for you!");
    }

    public static JMenuItem I(String text, char menumonic, Icon icon, VoidFunc1Arg<ActionEvent> onClick) {
        var out = I(text, menumonic, onClick);
        out.setIcon(icon);
        return out;
    }

    public static JMenuItem I(String text, char menumonic, VoidFunc1Arg<ActionEvent> onClick) {
        var out = new JMenuItem(text);
        out.addActionListener(e -> onClick.run(e));
        if (menumonic != 0)
            out.setMnemonic(menumonic);
        return out;
    }

    public static JMenu M(String text, char menumonic, JMenuItem... items) {
        var out = new JMenu(text);
        if (menumonic != 0)
            out.setMnemonic(menumonic);
        for (JMenuItem i : items)
            out.add(i);
        return out;
    }

    public static JCheckBoxMenuItem C(String text, char menumonic, VoidFunc1Arg<ActionEvent> onClick) {
        var out = new JCheckBoxMenuItem(text, false);
        out.addActionListener(e -> onClick.run(e));
        if (menumonic != 0)
            out.setMnemonic(menumonic);
        return out;
    }

    public static JCheckBoxMenuItem C(String text) {
        var out = new JCheckBoxMenuItem(text, false);
        return out;
    }

    public static JMenuItem[] CG(VoidFunc1Arg<Integer> callback, Integer initialState, JCheckBoxMenuItem... items) {
        new CBG(callback, initialState, items);
        return items;
    }

    public static void setMenus(JFrame window, JMenu... items) {
        var bar = new JMenuBar();
        for (JMenuItem i : items)
            bar.add(i);
        window.setJMenuBar(bar);
    }

    private static class CBG {

        final JCheckBoxMenuItem[] items;
        final VoidFunc1Arg<Integer> callback;

        public CBG(VoidFunc1Arg<Integer> callback, Integer initialState, JCheckBoxMenuItem... items) {
            this.items = items;
            this.callback = callback;

            for (int i = 0; i < items.length; i++) {
                final int fi = i;
                items[i].addActionListener(e -> this.update(items[fi], e));
            }
        }

        void update(JCheckBoxMenuItem item, ActionEvent event) {
            if ((event.getModifiers() & ActionEvent.SHIFT_MASK) > 0)
                for (JCheckBoxMenuItem i : items)
                    if (i != item)
                        i.setSelected(!item.isSelected());
            int out = 0;
            for (int i = 0; i < items.length; i++)
                if (items[i].isSelected())
                    out += 1 << i;
            callback.run(out);
        }

        /*
         * private int indexOf(JCheckBoxMenuItem item) {
         * for (int i = 0; i < items.length; i++)
         * if(items[i]==item)
         * return i;
         * return -1;
         * }
         */
    }
}