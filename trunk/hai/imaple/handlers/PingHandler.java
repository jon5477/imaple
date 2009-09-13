/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.handlers;

import imaple.Core;
import imaple.network.JPacketHandler;
import imaple.network.input.SeekableLittleEndianAccessor;

/**
 *
 * @author Sactual
 */

public class PingHandler implements JPacketHandler {

    public static Core c;

    public void handle(SeekableLittleEndianAccessor slea) {
        c.sendPong();
    }

    public boolean validateState(Core c) {
	return true;
    }
}
