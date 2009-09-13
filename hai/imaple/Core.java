/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple;

import imaple.ex.RageZoneException;
import imaple.ex.YouAreTooFuckedException;
import imaple.network.NIOHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.zip.ZipFile;

/**
 *
 * @author David
 */
public class Core {
	public static CWindow window;
	public static final HashMap<DataFileType, ZipFile> data =
			new LinkedHashMap<DataFileType, ZipFile>();
	public static final boolean dbgMode = true;
	public static final NIOHandler networkHandler = new NIOHandler();
	public static int PORT = 8484;
	public static String IP = "127.0.0.1";
	public static BufferedReader jin;
	public static boolean fullscreen = false;

	public static enum DataFileType {
		CHARACTER,
		EFFECT,
		ETC,
		ITEM,
		LIST,
		MAP,
		STRING,
		UI,
		MOB,
		TAMINGMOB,
		SKILL,
		QUEST,
		REACTOR,
		NPC,
		MORPH,
		SOUND
	}

	public static final void printDbg(String line) {
		if (dbgMode) {
			System.err.println(line);
		}
	}

	private static void initiallizeData()
		throws YouAreTooFuckedException {
		DataFileType[] types = DataFileType.values();
		for (int x = 0; x < types.length; x++) {
			StringBuilder fName = new StringBuilder(types[x].name().toLowerCase());
			fName.replace(0, 1, String.valueOf(Character.toUpperCase(fName.charAt(0))));
			try {
				String fNameAsAFuckingString = fName.toString() + ".YOURMOM";
				printDbg("DataLoader:: Loading " + fNameAsAFuckingString);
				data.put(types[x], new ZipFile(fNameAsAFuckingString));
			} catch (IOException bitchhhhh) {
				throw new YouAreTooFuckedException(bitchhhhh);
			}
		}
	}

	public static final int itoa(String in) {
		return Integer.parseInt(in);
	}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] cocks)
		throws YouAreTooFuckedException, RageZoneException {
		for (int i = 0; i < cocks.length; i++) {
			if (cocks[i].equalsIgnoreCase("-ip") && i < cocks.length - 1) {
				IP = cocks[i + 1];
			}
			if (cocks[i].equalsIgnoreCase("-port") && i < cocks.length - 1) {
				try {
					PORT = itoa(cocks[i + 1]);
				} catch (NumberFormatException ragezone) {
					throw new RageZoneException(ragezone);
				}
			}
			if (cocks[i].equalsIgnoreCase("-fs")) {
				fullscreen = FullScreenDevice.isDisplayChangeAvailable() && FullScreenDevice.isExclusiveModeAvailable();
			}
		}
		try {
			networkHandler.connectTo(new InetSocketAddress(IP, PORT));
		} catch (YouAreTooFuckedException fucknooooooooooooooo) {
			System.err.println("Cannot connect to the server.");
			fucknooooooooooooooo.printStackTrace();
			System.exit(1);
		}
		printDbg("Connected to Login Server.");
		try {
			initiallizeData();
		} catch (YouAreTooFuckedException youarefuckedomgoshhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhnoooooooooooooooooooooooooooooooooooooo) {
			youarefuckedomgoshhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhnoooooooooooooooooooooooooooooooooooooo.printStackTrace();
		}
		window = new CWindow();
		window.initiallize(fullscreen);
		window.setVisible(true);
		jin = new BufferedReader(new InputStreamReader(System.in));
		if (fullscreen) {
			FullScreenDevice.setFullScreen(window);
			FullScreenDevice.changeDisplayMode(FullScreenDevice.msDisplayMode);
		}
    }
}