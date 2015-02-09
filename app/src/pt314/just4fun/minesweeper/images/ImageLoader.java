package pt314.just4fun.minesweeper.images;

import javax.swing.ImageIcon;

public class ImageLoader {
	
    /**
     * Returns an ImageIcon, or null if the path was invalid.
     * Modified code from:
     * http://docs.oracle.com/javase/tutorial/uiswing/components/icon.html
     */
    public static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ImageLoader.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
