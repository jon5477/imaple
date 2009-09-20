/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author David
 */
public abstract class GameObject {
	protected int
			x,
			y
	;
	protected Rectangle area;

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

	public void onMousePressed(MouseEvent evt) {

	}

	public void onMouseReleased(MouseEvent evt) {

	}

	public void onMouseMoved(MouseEvent evt) {

	}

	public void onMouseDragged(MouseEvent evt) {

	}

	public void onKeyPressed(KeyEvent evt) {

	}

	public void onKeyReleased(KeyEvent evt) {
		
	}

	public abstract void render(Graphics2D g);
}
