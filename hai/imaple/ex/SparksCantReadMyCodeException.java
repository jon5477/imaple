/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.ex;

/**
 *
 * @author David
 */
public class SparksCantReadMyCodeException extends RuntimeException {

	public SparksCantReadMyCodeException(Throwable cause) {
		super(cause);
	}

	public SparksCantReadMyCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SparksCantReadMyCodeException(String message) {
		super(message);
	}

	public SparksCantReadMyCodeException() {
	}

}
