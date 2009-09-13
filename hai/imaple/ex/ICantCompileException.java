/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.ex;

/**
 *
 * @author David
 */
public class ICantCompileException extends Exception {

	public ICantCompileException(Throwable cause) {
		super(cause);
	}

	public ICantCompileException(String message, Throwable cause) {
		super(message, cause);
	}

	public ICantCompileException(String message) {
		super(message);
	}

	public ICantCompileException() {
	}

}
