/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.handlers;

import imaple.Core;
import imaple.network.AESOFB;
import imaple.network.JPacketHandler;
import imaple.network.input.SeekableLittleEndianAccessor;
import java.awt.SystemTray;
import java.awt.TrayIcon.MessageType;

/**
 *
 * @author Seth
 */

public class IVHandler implements JPacketHandler { // Handles the HELLO packet sent from the server..
    public void handle(SeekableLittleEndianAccessor slea) {
	short encVersion = slea.readShort(); // Client Version ENCRYPTED WITH AES
        slea.readByte(); // Maybe determines Administrator Client?
        slea.readByte(); // Maybe determines Administrator Client?
        byte[] recvIV = {slea.readByte(), slea.readByte(), slea.readByte(), slea.readByte(), slea.readByte()}; // Recieved Packet IV
        byte[] sendIV = {slea.readByte(), slea.readByte(), slea.readByte(), slea.readByte(), slea.readByte()}; // Sent packet IV
        slea.readByte(); // We are NOT gonna use this... (Test Server Determination)
        byte aesKey[] = { 0x13, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, (byte) 0xB4, 0x00, 0x00, 0x00, 0x1B, 0x00, 0x00, 0x00, 0x0F, 0x00, 0x00, 0x00, 0x33, 0x00, 0x00, 0x00, 0x52, 0x00, 0x00, 0x00};
        Core.setSendIV(new AESOFB(aesKey, sendIV, (short) (0xFFFF - encVersion)));
	Core.setRecvIV(new AESOFB(aesKey, recvIV, encVersion));
        System.err.println("iMaple Server Version: " + Short.toString(encVersion));
        Short decVersion = (short) (((encVersion >> 8) & 0xFF) | ((encVersion << 8) & 0xFF00));
        if (decVersion != Short.decode(Integer.toString(Core.getVersion()))) { // Not the best way...
            System.err.println("Both versions from server and client are not the same. Changing client version..");
            Core.setVersion(decVersion);
            if (SystemTray.isSupported()) {
                Core.trayIcon.displayMessage("[iMaple v." + Core.getVersion() +"]", "iMaple's default version has been changed to " + Core.getVersion() + "", MessageType.INFO);
            }
        }
    }
}
