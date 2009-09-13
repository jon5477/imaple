/*
 * Haiiiiiiii faggot
 */

package imaple.network.output;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.mina.common.ByteBuffer;

public class ByteBufferOutputstream implements ByteOutputStream {
    private ByteBuffer bb;

    /**
     * Class constructor - Wraps this instance around ByteBuffer <code>bb</code>
     *
     * @param bb The <code>renoria.net.mina.common.ByteBuffer</code> to wrap
     *            this stream around.
     */
    public ByteBufferOutputstream(ByteBuffer bb) {
        super();
        this.bb = bb;
    }

    /**
     * Writes a byte to the underlying buffer.
     *
     * @param b The byte to write.
     * @see renoria.net.mina.common.ByteBuffer#put(byte)
     */
    @Override
    public void writeByte(byte b) {
        bb.put(b);
    }
}
