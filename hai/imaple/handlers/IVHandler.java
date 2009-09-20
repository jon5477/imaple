/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.handlers;

import imaple.Core;
import imaple.network.AESOFB;
import imaple.network.JPacketHandler;
import imaple.network.input.SeekableLittleEndianAccessor;

/**
 *
 * @author Seth
 */

public class IVHandler implements JPacketHandler { // Handles the HELLO packet sent from the server..
    public void handle(SeekableLittleEndianAccessor slea) {
	short encVersion = slea.readShort(); // Client Version ENCRYPTED WITH AES
        slea.readByte(); // Maybe determines Administrator Client?
        slea.readByte(); // Maybe determines Administrator Client?
        byte[] recvIV = {slea.readByte()}; // Recieved Packet IV
        byte[] sendIV = {slea.readByte()}; // Sent packet IV
        slea.readByte(); // We are NOT gonna use this... (Test Server Determination)

        byte key[] = { 0x13, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, (byte) 0xB4, 0x00, 0x00,
			0x00, 0x1B, 0x00, 0x00, 0x00, 0x0F, 0x00, 0x00, 0x00, 0x33, 0x00, 0x00, 0x00, 0x52, 0x00, 0x00, 0x00 };

        Core.setSendIV(new AESOFB(key, sendIV, (short) (0xFFFF - encVersion)));
	Core.setRecvIV(new AESOFB(key, recvIV, encVersion));
    }
}
