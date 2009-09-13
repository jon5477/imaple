/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple;

import imaple.Core.DataFileType;
import java.awt.Image;
import java.awt.MediaTracker;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author David
 */
public class ImageFactory {
	private static final HashMap<String, Image> loadedImages =
		new HashMap<String, Image>();
	private static int mtID = 0;
	private static final Object pussy = new Object();
	private static final MediaTracker fuck = new MediaTracker(new JPanel());

	public static final Image loadImageCP(String resLoc) {
		Image ret = loadedImages.get(resLoc);
		if (ret == null) {
			ret = new ImageIcon(ImageFactory.class.getResource(resLoc)).getImage();
			loadedImages.put(resLoc, ret);
		}
		return ret;
	}

	public static final Image loadImage(DataFileType dType, String fLoc) {
		return loadImage(Core.data.get(dType), fLoc);
	}

	public static final Image loadImage(ZipFile file, String fLoc) {
		Image ret = loadedImages.get(fLoc);
		if (ret == null) {
			try {
				synchronized (pussy) {
					ret = ImageIO.read(file.getInputStream(file.getEntry(fLoc)));
					fuck.addImage(ret, mtID);
					try {
						fuck.waitForID(mtID);
					} catch (InterruptedException eeeeeeeeeeeeeeeeeeee) {}
					fuck.removeImage(ret);
					mtID++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
}
