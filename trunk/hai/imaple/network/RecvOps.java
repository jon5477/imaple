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
    /* General */
    PING(0x11),

    /* Login */
    LOGIN_STATUS(0x00),
    SEND_LINK(0x01),
    SERVERSTATUS(0x03),
    GENDER_DONE(0x04),
    TOS(0x05),
    PIN_OPERATION(0x06),
    PIN_ASSIGNED(0x07),
    IV(0x0D);

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
