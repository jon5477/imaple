/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.network;

import imaple.util.BitTools;
import imaple.util.HexTool;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Sactual
 */
public class AESOFB {
	private byte iv[];
	private Cipher cipher;
	private short mapleVersion;
	private static final byte[] funnyBytes = new byte[] { (byte) 0xEC, 0x3F, 0x77, (byte) 0xA4, 0x45, (byte) 0xD0,
		0x71, (byte) 0xBF, (byte) 0xB7, (byte) 0x98, 0x20, (byte) 0xFC, 0x4B, (byte) 0xE9, (byte) 0xB3, (byte) 0xE1,
		0x5C, 0x22, (byte) 0xF7, 0x0C, 0x44, 0x1B, (byte) 0x81, (byte) 0xBD, 0x63, (byte) 0x8D, (byte) 0xD4,
		(byte) 0xC3, (byte) 0xF2, 0x10, 0x19, (byte) 0xE0, (byte) 0xFB, (byte) 0xA1, 0x6E, 0x66, (byte) 0xEA,
		(byte) 0xAE, (byte) 0xD6, (byte) 0xCE, 0x06, 0x18, 0x4E, (byte) 0xEB, 0x78, (byte) 0x95, (byte) 0xDB,
		(byte) 0xBA, (byte) 0xB6, 0x42, 0x7A, 0x2A, (byte) 0x83, 0x0B, 0x54, 0x67, 0x6D, (byte) 0xE8, 0x65,
		(byte) 0xE7, 0x2F, 0x07, (byte) 0xF3, (byte) 0xAA, 0x27, 0x7B, (byte) 0x85, (byte) 0xB0, 0x26, (byte) 0xFD,
		(byte) 0x8B, (byte) 0xA9, (byte) 0xFA, (byte) 0xBE, (byte) 0xA8, (byte) 0xD7, (byte) 0xCB, (byte) 0xCC,
		(byte) 0x92, (byte) 0xDA, (byte) 0xF9, (byte) 0x93, 0x60, 0x2D, (byte) 0xDD, (byte) 0xD2, (byte) 0xA2,
		(byte) 0x9B, 0x39, 0x5F, (byte) 0x82, 0x21, 0x4C, 0x69, (byte) 0xF8, 0x31, (byte) 0x87, (byte) 0xEE,
		(byte) 0x8E, (byte) 0xAD, (byte) 0x8C, 0x6A, (byte) 0xBC, (byte) 0xB5, 0x6B, 0x59, 0x13, (byte) 0xF1, 0x04,
		0x00, (byte) 0xF6, 0x5A, 0x35, 0x79, 0x48, (byte) 0x8F, 0x15, (byte) 0xCD, (byte) 0x97, 0x57, 0x12, 0x3E, 0x37,
		(byte) 0xFF, (byte) 0x9D, 0x4F, 0x51, (byte) 0xF5, (byte) 0xA3, 0x70, (byte) 0xBB, 0x14, 0x75, (byte) 0xC2,
		(byte) 0xB8, 0x72, (byte) 0xC0, (byte) 0xED, 0x7D, 0x68, (byte) 0xC9, 0x2E, 0x0D, 0x62, 0x46, 0x17, 0x11, 0x4D,
		0x6C, (byte) 0xC4, 0x7E, 0x53, (byte) 0xC1, 0x25, (byte) 0xC7, (byte) 0x9A, 0x1C, (byte) 0x88, 0x58, 0x2C,
		(byte) 0x89, (byte) 0xDC, 0x02, 0x64, 0x40, 0x01, 0x5D, 0x38, (byte) 0xA5, (byte) 0xE2, (byte) 0xAF, 0x55,
		(byte) 0xD5, (byte) 0xEF, 0x1A, 0x7C, (byte) 0xA7, 0x5B, (byte) 0xA6, 0x6F, (byte) 0x86, (byte) 0x9F, 0x73,
		(byte) 0xE6, 0x0A, (byte) 0xDE, 0x2B, (byte) 0x99, 0x4A, 0x47, (byte) 0x9C, (byte) 0xDF, 0x09, 0x76,
		(byte) 0x9E, 0x30, 0x0E, (byte) 0xE4, (byte) 0xB2, (byte) 0x94, (byte) 0xA0, 0x3B, 0x34, 0x1D, 0x28, 0x0F,
		0x36, (byte) 0xE3, 0x23, (byte) 0xB4, 0x03, (byte) 0xD8, (byte) 0x90, (byte) 0xC8, 0x3C, (byte) 0xFE, 0x5E,
		0x32, 0x24, 0x50, 0x1F, 0x3A, 0x43, (byte) 0x8A, (byte) 0x96, 0x41, 0x74, (byte) 0xAC, 0x52, 0x33, (byte) 0xF0,
		(byte) 0xD9, 0x29, (byte) 0x80, (byte) 0xB1, 0x16, (byte) 0xD3, (byte) 0xAB, (byte) 0x91, (byte) 0xB9,
		(byte) 0x84, 0x7F, 0x61, 0x1E, (byte) 0xCF, (byte) 0xC5, (byte) 0xD1, 0x56, 0x3D, (byte) 0xCA, (byte) 0xF4,
		0x05, (byte) 0xC6, (byte) 0xE5, 0x08, 0x49, 0x4F, 0x64, 0x69, 0x6E, 0x4D, 0x53, 0x7E, 0x46, 0x72, 0x7A };

	private org.slf4j.Logger log = LoggerFactory.getLogger(AESOFB.class);

	/**
	 * Class constructor - Creates an instance of the MapleStory encryption
	 * cipher.
	 *
	 * @param key The 256 bit AES key to use.
	 * @param iv The 4-byte IV to use.
	 */
	public AESOFB(byte key[], byte iv[], short mapleVersion) {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

		try {
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			log.error("ERROR", e);
		} catch (NoSuchPaddingException e) {
			log.error("ERROR", e);
		}
		try {
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		} catch (InvalidKeyException e) {
			log.error("Error initalizing the encryption cipher.  Make sure you're using the Unlimited Strength cryptography jar files.");
		}

		this.setIv(iv);
		this.mapleVersion = (short) (((mapleVersion >> 8) & 0xFF) | ((mapleVersion << 8) & 0xFF00));
	}

	/**
	 * Sets the IV of this instance.
	 *
	 * @param iv The new IV.
	 */
	private void setIv(byte[] iv) {
		this.iv = iv;
	}

	/**
	 * For debugging/testing purposes only.
	 *
	 * @return The IV.
	 */
	public byte[] getIv() {
		return this.iv;
	}

	/**
	 * Encrypts <code>data</code> and generates a new IV.
	 *
	 * @param data The bytes to encrypt.
	 * @return The encrypted bytes.
	 */
	public byte[] crypt(byte[] data) {
		int remaining = data.length;
		int llength = 0x5B0;
		int start = 0;
		while (remaining > 0) {
			byte[] myIv = BitTools.multiplyBytes(this.iv, 4, 4);
			if (remaining < llength) {
				llength = remaining;
			}
			for (int x = start; x < (start + llength); x++) {
				if ((x - start) % myIv.length == 0) {
					try {
						byte[] newIv = cipher.doFinal(myIv);
						for (int j = 0; j < myIv.length; j++) {
							myIv[j] = newIv[j];
						}
						// System.out
						// .println("Iv is now " + HexTool.toString(this.iv));
					} catch (IllegalBlockSizeException e) {
						e.printStackTrace();
					} catch (BadPaddingException e) {
						e.printStackTrace();
					}
				}
				data[x] ^= myIv[(x - start) % myIv.length];
			}
			start += llength;
			remaining -= llength;
			llength = 0x5B4;
		}
		updateIv();
		return data;
	}

	/**
	 * Generates a new IV.
	 */
	private void updateIv() {
		this.iv = getNewIv(this.iv);
	}

	/**
	 * Generates a packet header for a packet that is <code>length</code>
	 * long.
	 *
	 * @param length How long the packet that this header is for is.
	 * @return The header.
	 */
	public byte[] getPacketHeader(int length) {
		int iiv = (iv[3]) & 0xFF;
		iiv |= (iv[2] << 8) & 0xFF00;

		iiv ^= mapleVersion;
		int mlength = ((length << 8) & 0xFF00) | (length >>> 8);
		int xoredIv = iiv ^ mlength;

		byte[] ret = new byte[4];
		ret[0] = (byte) ((iiv >>> 8) & 0xFF);
		ret[1] = (byte) (iiv & 0xFF);
		ret[2] = (byte) ((xoredIv >>> 8) & 0xFF);
		ret[3] = (byte) (xoredIv & 0xFF);
		return ret;
	}

	/**
	 * Gets the packet length from a header.
	 *
	 * @param packetHeader The header as an integer.
	 * @return The length of the packet.
	 */
	public static int getPacketLength(int packetHeader) {
		int packetLength = ((packetHeader >>> 16) ^ (packetHeader & 0xFFFF));
		packetLength = ((packetLength << 8) & 0xFF00) | ((packetLength >>> 8) & 0xFF); // fix
		// endianness
		return packetLength;
	}

	/**
	 * Check the packet to make sure it has a header.
	 *
	 * @param packet The packet to check.
	 * @return <code>True</code> if the packet has a correct header,
	 *         <code>false</code> otherwise.
	 */
	public boolean checkPacket(byte[] packet) {
		return ((((packet[0] ^ iv[2]) & 0xFF) == ((mapleVersion >> 8) & 0xFF)) && (((packet[1] ^ iv[3]) & 0xFF) == (mapleVersion & 0xFF)));
	}

	/**
	 * Check the header for validity.
	 *
	 * @param packetHeader The packet header to check.
	 * @return <code>True</code> if the header is correct, <code>false</code>
	 *         otherwise.
	 */
	public boolean checkPacket(int packetHeader) {
		byte packetHeaderBuf[] = new byte[2];
		packetHeaderBuf[0] = (byte) ((packetHeader >> 24) & 0xFF);
		packetHeaderBuf[1] = (byte) ((packetHeader >> 16) & 0xFF);
		return checkPacket(packetHeaderBuf);
	}

	/**
	 * Gets a new IV from <code>oldIv</code>
	 *
	 * @param oldIv The old IV to get a new IV from.
	 * @return The new IV.
	 */
	public static byte[] getNewIv(byte oldIv[]) {
		byte[] in = { (byte) 0xf2, 0x53, (byte) 0x50, (byte) 0xc6 }; // magic
		// ;)
		for (int x = 0; x < 4; x++) {
			funnyShit(oldIv[x], in);
			// System.out.println(HexTool.toString(in));
		}
		return in;
	}

	/**
	 * Returns the IV of this instance as a string.
	 */
	@Override
	public String toString() {
		return "IV: " + HexTool.toString(this.iv);
	}

	/**
	 * Does funny stuff. <code>this.OldIV</code> must not equal
	 * <code>in</code> Modifies <code>in</code> and returns it for
	 * convenience.
	 *
	 * @param inputByte The byte to apply the funny stuff to.
	 * @param in Something needed for all this to occur.
	 * @return The modified version of <code>in</code>.
	 */
	public static byte[] funnyShit(byte inputByte, byte[] in) {
		byte elina = in[1];
		byte anna = inputByte;
		byte moritz = funnyBytes[(int) elina & 0xFF];
		moritz -= inputByte;
		in[0] += moritz;
		moritz = in[2];
		moritz ^= funnyBytes[(int) anna & 0xFF];
		elina -= (int) moritz & 0xFF;
		in[1] = elina;
		elina = in[3];
		moritz = elina;
		elina -= (int) in[0] & 0xFF;
		moritz = funnyBytes[(int) moritz & 0xFF];
		moritz += inputByte;
		moritz ^= in[2];
		in[2] = moritz;
		elina += (int) funnyBytes[(int) anna & 0xFF] & 0xFF;
		in[3] = elina;

		int merry = ((int) in[0]) & 0xFF;
		merry |= (in[1] << 8) & 0xFF00;
		merry |= (in[2] << 16) & 0xFF0000;
		merry |= (in[3] << 24) & 0xFF000000;
		int ret_value = merry;
		ret_value = ret_value >>> 0x1d;
		merry = merry << 3;
		ret_value = ret_value | merry;

		in[0] = (byte) (ret_value & 0xFF);
		in[1] = (byte) ((ret_value >> 8) & 0xFF);
		in[2] = (byte) ((ret_value >> 16) & 0xFF);
		in[3] = (byte) ((ret_value >> 24) & 0xFF);

		return in;
	}
}
