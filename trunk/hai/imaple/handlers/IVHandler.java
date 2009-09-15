/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.handlers;

import imaple.network.JPacketHandler;
import imaple.network.input.SeekableLittleEndianAccessor;

/**
 *
 * @author Seth
 */

public class IVHandler implements JPacketHandler { // Handles the HELLO packet sent from the server..
    public void handle(SeekableLittleEndianAccessor slea) {
	int encVersion = slea.readShort(); // Client Version ENCRYPTED WITH AES
        slea.readByte(); // Maybe determines Administrator Client?
        slea.readByte(); // Maybe determines Administrator Client?
        byte recvIV = slea.readByte(); // Recieved Packet IV
        byte sendIV = slea.readByte(); // Sent packet IV
        slea.readByte(); // We are NOT gonna use this... (Test Server Determination)


    }
}
