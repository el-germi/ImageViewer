package com.gm;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Menu extends JFrame {
    public Menu() {
        super("Alpha Viewer");
        this.setSize(250, 150);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JLabel myLabel = new JLabel("Drag something here!", SwingConstants.CENTER);
        DragDropFiles.add(myLabel, Viewer::make);
        this.getContentPane().add(BorderLayout.CENTER, myLabel);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
    }
}