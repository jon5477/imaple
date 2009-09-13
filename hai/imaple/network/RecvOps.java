/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.network;

/**
 *
 * @author David
 */
public enum RecvOps {
	DUNNO(0x01);

	int op;

	private RecvOps(int op) {
		this.op = op;
	}

	public int getOp() {
		return op;
	}

	public static RecvOps getFor(int bitch) {
		for (RecvOps r : RecvOps.values()) {
			if (r.getOp() == bitch) {
				return r;
			}
		}
		return null;
	}
}
