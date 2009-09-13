/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.network;

import imaple.network.output.PacketWriter;

/**
 * Handles most sent packets
 * that are sent from the server
 *
 * @author Haiku01
 * @version 9.999
 *
 */

public class PacketCreator {
    public static Packet getPong() {
        PacketWriter pw = new PacketWriter();
        pw.writeShort(SendOps.PONG.getOp());
        return pw.getPacket();
    }

}
