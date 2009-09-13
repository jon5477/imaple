/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.ex;

/**
 *
 * @author David
 */
public class RageZoneException extends Exception {

	public RageZoneException(Throwable cause) {
		super(cause);
	}

	public RageZoneException(String message, Throwable cause) {
		super(message, cause);
	}

	public RageZoneException(String message) {
		super(message);
	}

	public RageZoneException() {
	}

}
