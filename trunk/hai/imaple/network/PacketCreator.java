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

    /* Sends the PONG packet */
    public static Packet getPong() {
        PacketWriter pw = new PacketWriter();
        pw.writeShort(SendOps.PONG.getOp());
        return pw.getPacket();
    }

    /**
     * Sends the login and password to the server
     * for verification.
     *
     * @param user The username that will be processed
     * @param pass The password that will be processed
     * The server reads both the username and password
     *  originally as a short that is then turn into a
     *  ASCII String.
     * 
     * @return
     */
    public static Packet sendLogin(String user, String pass) {
        PacketWriter pw = new PacketWriter();
        pw.writeShort(SendOps.LOGIN_PASSWORD.getOp());
        pw.writeShort(Short.decode(user));
        pw.writeShort(Short.decode(pass));
        return pw.getPacket();
    }
}
