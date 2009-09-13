/*
 * Haiiiiiiii faggot
 */

package imaple.network.output;

//~--- non-JDK imports --------------------------------------------------------

import imaple.network.Packet;

import imaple.util.HexTool;

//~--- JDK imports ------------------------------------------------------------

import java.io.ByteArrayOutputStream;

/**
 * Writes a maplestory-packet little-endian stream of bytes.
 *
 * @author Frz
 * @version 1.0
 * @since Revision 352
 */
public class PacketWriter extends GenericLittleEndianWriter {
    private ByteArrayOutputStream baos;

    /**
     * Constructor - initializes this stream with a default size.
     */
    public PacketWriter() {
        this(32);
    }

    /**
     * Constructor - initializes this stream with size <code>size</code>.
     *
     * @param size The size of the underlying stream.
     */
    public PacketWriter(int size) {
        this.baos = new ByteArrayOutputStream(size);
        setByteOutputStream(new BAOSByteOutputStream(baos));
    }

    /**
     * Gets a <code>MaplePacket</code> instance representing this
     * sequence of bytes.
     *
     * @return A <code>MaplePacket</code> with the bytes in this stream.
     */
    public Packet getPacket() {

        // MaplePacket packet = new ByteArrayMaplePacket(baos.toByteArray());
        // System.out.println("Packet to be sent:\n" +packet.toString() + "\n\n");
        return Packet.wrap(baos.toByteArray());
    }

    /**
     * Changes this packet into a human-readable hexadecimal stream of bytes.
     *
     * @return This packet as hex digits.
     */
    @Override
    public String toString() {
        return HexTool.toString(baos.toByteArray());
    }
}
