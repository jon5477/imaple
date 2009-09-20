/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.network;

import imaple.Core;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 *
 * @author David (Special Encryption and Decryption by Seth)
 */

public class PacketCryptFactory implements ProtocolCodecFactory {
	Encoder e = new Encoder();
	Decoder d = new Decoder();

	public ProtocolEncoder getEncoder() throws Exception {
		return e;
	}

	public ProtocolDecoder getDecoder() throws Exception {
		return d;
	}

	final class Encoder implements ProtocolEncoder {
		public void encode(IoSession arg0, Object arg1, ProtocolEncoderOutput arg2) throws Exception {
			Packet yourfuck = (Packet) arg1;
			byte[] bytes = yourfuck.getPussy();
			ByteBuffer buffer = ByteBuffer.allocate(bytes.length + 4, false);
			buffer.putInt(bytes.length);
			buffer.put(bytes);
                        PacketEncryption.encryptData(bytes);
                        Core.getSendIV().crypt(bytes);
			arg2.write(buffer);
		}

		public void dispose(IoSession arg0) throws Exception {

		}
	}

	final class Decoder extends CumulativeProtocolDecoder {
		@Override
		protected boolean doDecode(IoSession arg0, ByteBuffer arg1, ProtocolDecoderOutput arg2) throws Exception {
			if (arg1.prefixedDataAvailable(4)) {
				int byteLen = arg1.getInt();
				byte[] shit = new byte[byteLen];
				arg1.get(shit);
                                Core.getRecvIV().crypt(shit);
                                PacketEncryption.decryptData(shit);
				arg2.write(shit);
				return true;
			} else {
				return false;
			}
		}
	}
}
