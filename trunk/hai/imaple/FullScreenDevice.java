/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple;

import imaple.ex.SparksCantReadMyCodeException;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;

/**
 *
 * @author David
 */
public class FullScreenDevice {
	private static final GraphicsDevice fsDevice;
	private static final DisplayMode originalDisplayMode;
	public static final DisplayMode msDisplayMode = new DisplayMode(800, 600, 32, 0);

	public static boolean isExclusiveModeAvailable() {
		return fsDevice.isFullScreenSupported();
	}

	public static boolean isDisplayChangeAvailable() {
		return fsDevice.isDisplayChangeSupported();
	}

	public static void setFullScreen(JFrame frame) {
		if (!isExclusiveModeAvailable()) {
			throw new SparksCantReadMyCodeException("FS IS NOT AVAILABLE, USE isExclusiveModeAvailable() first you retard.");
		}
		fsDevice.setFullScreenWindow(frame);
	}

	public static void changeDisplayMode(DisplayMode nDisplayMode) {
		if (!isDisplayChangeAvailable()) {
			throw new SparksCantReadMyCodeException("Omfg, Changing the display is not available on this machine, I suggest you buy another pc.");
		}
		fsDevice.setDisplayMode(nDisplayMode);
	}

	public static DisplayMode getOriginalDisplayMode() {
		return originalDisplayMode;
	}

	static {
		fsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		originalDisplayMode = fsDevice.getDisplayMode();
	}
}
