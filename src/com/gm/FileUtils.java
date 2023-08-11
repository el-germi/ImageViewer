package com.gm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author germimonte@hotmail.com
 */
public final class FileUtils {
	// Suppresses default constructor, ensuring non-instantiability.
	private FileUtils() {
		throw new UnsupportedOperationException("No " + this.getClass() + " instances for you!");
	}

	/**
	 * FileFilter for all file extensions supported by 'ImageIO.read()'
	 */
	public static final FileFilter IMAGES = new FileNameExtensionFilter("Images", //
			"jpg", "jpeg", "jfif", "pjpeg", "pjp", "jif", "jpe", "jfi", //
			"png", //
			"gif", //
			"wbmp", //
			"bmp", "dib", //
			"tiff", "tif");

	public static final BufferedImage getImage(File f) {
		try {
			return ImageIO.read(f);
		} catch (Exception e) {
			return null;
		}
	}

    /**
	 * @param filter for file selection, or null to accept any file.
	 * @param dir that represents starting directory, or null to start at home.
	 */
    public static final File openFile(FileFilter filter, File dir) {
        var chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(0);// JFileChooser.FILES_ONLY
        chooser.setCurrentDirectory(dir);
        if (filter != null) {
            chooser.setFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);
        }
        if (chooser.showOpenDialog(null) == 0)// JFileChooser.APPROVE_OPTION
            return chooser.getSelectedFile();
        return null;
    }

    public static final BufferedImage openImage(File dir) {
        return getImage(openFile(IMAGES, dir));
    }

	public static final InputStream getInternalResource(String path) {
		try {
			return FileUtils.class.getResourceAsStream(path);
		} catch (Exception e) {
			return null;
		}
	}

	public static final BufferedImage getInternalImage(String path) {
		try {
			return ImageIO.read(getInternalResource(path));
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] getInternalBytes(String string) {
		try {
			return getInternalResource(string).readAllBytes();
		} catch (IOException e) {
			return null;
		}
	}
}
