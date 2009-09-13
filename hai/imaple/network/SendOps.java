/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.network;

/**
 *
 * @author David
 */

public enum SendOps {
    /* General */
    PONG(0x19);

    int op;

    private SendOps(int op) {
	this.op = op;
    }

    public int getOp() {
	return op;
    }

    public static SendOps getFor(int bitch) {
	for (SendOps s : SendOps.values()) {
            if (s.getOp() == bitch) {
		return s;
            }
	}
	return null;
    }
}
