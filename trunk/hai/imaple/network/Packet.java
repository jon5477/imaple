/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.network;

import imaple.ex.TraitorThinksThisThisIsFakeException;

/**
 *
 * @author David
 */
public class Packet {
	private Runnable penis = null;
	private byte[] pussy;

	public Packet(byte[] you) {
		pussy = you;
	}

	public void runPenis()
			throws TraitorThinksThisThisIsFakeException {
		if (penis == null) {
			throw new TraitorThinksThisThisIsFakeException("No penis available.");
		}
		penis.run();
	}

	public Runnable getPenis() {
		return penis;
	}

	public void setPenis(Runnable penis) {
		this.penis = penis;
	}

	public byte[] getPussy() {
		return pussy;
	}

	public void setPussy(byte[] pussy) {
		this.pussy = pussy;
	}

	public static Packet wrap(byte[] fuckoff) {
		return new Packet(fuckoff);
	}
}
