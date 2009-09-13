/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple;

import imaple.ex.CoreyIsAFaggotException;
import imaple.ex.ICantCompileException;
import imaple.ex.RageZoneException;
import imaple.ex.YouAreTooFuckedException;
import imaple.network.NIOHandler;
import imaple.network.PacketCreator;
import imaple.util.TimerManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipFile;

/**
 *
 * @author David
 */
public class Core {
    public static MainWindow window;
    public static final HashMap<DataFileType, ZipFile> data =
        new LinkedHashMap<DataFileType, ZipFile>();
    public static final boolean dbgMode = true;
    public static final NIOHandler networkHandler = new NIOHandler();
    public static int PORT = 8484;
    public static long lastPing;
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

    private static void initiallizeData() throws YouAreTooFuckedException {
	DataFileType[] types = DataFileType.values();
	for (int x = 0; x < types.length; x++) {
            StringBuilder fName = new StringBuilder(types[x].name().toLowerCase());
            fName.replace(0, 1, String.valueOf(Character.toUpperCase(fName.charAt(0))));
            try {
		String fNameAsString = fName.toString() + ".IDA";
		printDbg("[IDALoader] Loading: " + fNameAsString);
		data.put(types[x], new ZipFile(fNameAsString));
            } catch (IOException zafuckz) {
		throw new YouAreTooFuckedException(zafuckz);
            }
        }
    }

    public static final int itoa(String in) {
	return Integer.parseInt(in);
    }

    public static void main(String[] main) throws YouAreTooFuckedException, RageZoneException {
	for (int i = 0; i < main.length; i++) {
            if (main[i].equalsIgnoreCase("-ip") && i < main.length - 1) {
		IP = main[i + 1];
            }
            if (main[i].equalsIgnoreCase("-port") && i < main.length - 1) {
		try {
                    PORT = itoa(main[i + 1]);
		} catch (NumberFormatException ragezone) {
                    throw new RageZoneException(ragezone);
		}
            }
            if (main[i].equalsIgnoreCase("-fs")) {
		fullscreen = FullScreenDevice.isDisplayChangeAvailable() && FullScreenDevice.isExclusiveModeAvailable();
            }
	}
	try {
            networkHandler.connectTo(new InetSocketAddress(IP, PORT));
	} catch (YouAreTooFuckedException yatfe) {
            System.err.println("Cannot connect to the server.");
            yatfe.printStackTrace();
            System.exit(1);
	}
	printDbg("Connected to Login Server.");
	try {
            initiallizeData();
        } catch (YouAreTooFuckedException omgwtfbbq) {
            omgwtfbbq.printStackTrace();
	}
	window = new MainWindow();
	window.initiallize(fullscreen);
	window.setVisible(true);
	jin = new BufferedReader(new InputStreamReader(System.in));
	if (fullscreen) {
            FullScreenDevice.setFullScreen(window);
            FullScreenDevice.changeDisplayMode(FullScreenDevice.msDisplayMode);
	}
    }
    
    public NIOHandler getSession() {
        return networkHandler;
    }

    public void pingReceived() {
	lastPing = System.currentTimeMillis();
    }

    public void sendPong() {
	final long then = System.currentTimeMillis();
        try {
            getSession().sendPacket(PacketCreator.getPong());
        } catch (CoreyIsAFaggotException ex) {
            Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ICantCompileException ex) {
            Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
        }
	TimerManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
		try {
                    if (lastPing - then < 0) {
			if (getSession().isConnected()) {
                            System.out.print("The pong packet has not succesfully been sent to the server.");
                            System.exit(1);
			}
                    }
		} catch (NullPointerException e) {
                    // Client has already been closed.
		}
            }
	}, 15000);
    }
}