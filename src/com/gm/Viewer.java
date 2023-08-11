package com.gm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import static com.gm.MenuUtils.*;

public class Viewer extends JFrame {

    private JLabel img;
    private BufferedImage picture;

    private Viewer(File f) throws IOException {
        super(f.getName());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();

        picture = FileUtils.getImage(f);
        img = new JLabel();
        int initialState = 0b1000;
        redo(initialState);
        panel.add(img);
        this.setSize(500, 500);
        var scrp = new JScrollPane(panel);
        this.getContentPane().add(scrp);
        MenuUtils.setMenus(this,
                M("Channels", 'C', CG(this::redo, initialState, C("Blue"), C("Green"), C("Red"), C("Alpha"))));
        this.setVisible(true);
    }

    private void redo(int i) {
        // System.out.println(Integer.toBinaryString(i));

        if (i == 0b1000)
            img.setIcon(new ImageIcon(filter1(picture)));
        else {
            int y = 0;
            for (int x = 0; x < 4; x++)
                y += (((1 << x) & i) > 0) ? 255 << (x * 8) : 0;
            img.setIcon(new ImageIcon(filter2(picture, y, (i > 0b1000) ? 2 : 1)));
        }
    }

    private static int byteToCol(int a) {
        a += (a << 8) + (a << 16);
        return a;
    }

    private static BufferedImage filter1(BufferedImage i) {
        BufferedImage out = new BufferedImage(i.getWidth(), i.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < i.getWidth(); x++)
            for (int y = 0; y < i.getHeight(); y++)
                out.setRGB(x, y, byteToCol(i.getRGB(x, y) >> (8 * 3)));
        return out;
    }

    private static BufferedImage filter2(BufferedImage i, int j, int type) {
        BufferedImage out = new BufferedImage(i.getWidth(), i.getHeight(), type);
        for (int x = 0; x < i.getWidth(); x++)
            for (int y = 0; y < i.getHeight(); y++)
                out.setRGB(x, y, i.getRGB(x, y) & j);
        return out;
    }

    public static void make(File f) {
        if (f != null)
            try {
                new Viewer(f);
            } catch (Exception e) {
                // do nothing
            }
    }
}