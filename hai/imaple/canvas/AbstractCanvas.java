/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.canvas;

import imaple.GameObject;
import imaple.ObjectList;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author David
 */
public abstract class AbstractCanvas {
	private final ObjectList objList = new ObjectList();

    public int getRenderDelay() {
		return 10;
    }

    public int getLogicDelay() {
		return 30;
    }

	public void defaultMousePressed(MouseEvent evt) {
		for (GameObject o : objList.getObjList()) {
			o.onMousePressed(evt);
		}
	}

	public void defaultMouseReleased(MouseEvent evt) {
		for (GameObject o : objList.getObjList()) {
			o.onMouseReleased(evt);
		}
	}

	public void defaultMouseMoved(MouseEvent evt) {
		for (GameObject o : objList.getObjList()) {
			o.onMouseMoved(evt);
		}
	}

	public void defaultMouseDragged(MouseEvent evt) {
		for (GameObject o : objList.getObjList()) {
			o.onMouseDragged(evt);
		}
	}

	public void defaultKeyPressed(KeyEvent evt) {
		for (GameObject o : objList.getObjList()) {
			o.onKeyPressed(evt);
		}
	}

	public void defaultKeyReleased(KeyEvent evt) {
		for (GameObject o : objList.getObjList()) {
			o.onKeyReleased(evt);
		}
	}

    public void onMousePressed(MouseEvent evt) {
		defaultMousePressed(evt);
    }

    public void onMouseReleased(MouseEvent evt) {
		defaultMouseReleased(evt);
    }

    public void onMouseMoved(MouseEvent evt) {
		defaultMouseMoved(evt);
    }

    public void onMouseDragged(MouseEvent evt) {
		defaultMouseDragged(evt);
    }

    public void onKeyPressed(KeyEvent evt) {
		defaultKeyPressed(evt);
    }

    public void onKeyReleased(KeyEvent evt) {
		defaultKeyReleased(evt);
    }

	public ObjectList getObjList() {
		return objList;
	}

    public abstract void doLogics();

    public abstract void doRender(Graphics2D g);
}
