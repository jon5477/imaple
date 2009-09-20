/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple;

import java.io.IOException;

/**
 *
 * @author David
 */
public class Hooker extends Thread {
	@Override public void run() {
		try {
			Core.closeData();
			if (Core.networkHandler != null) {
				Core.networkHandler.close();
			}
		} catch (IOException fuckyoubitch) {}
	}
}
