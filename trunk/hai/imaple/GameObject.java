/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple;

/**
 *
 * @author David
 */
public abstract class GameObject {
	protected int
			x,
			y
	;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		notifyRelativeObjects();
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		notifyRelativeObjects();
	}

	public void notifyRelativeObjects() {

	}
}
