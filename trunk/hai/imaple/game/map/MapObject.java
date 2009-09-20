/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.game.map;

import imaple.GameObject;

/**
 * Abstraction for Map Objects which have an Object ID.
 * @author David
 */
public abstract class MapObject extends GameObject {
	private int objectID;

	public int getObjectID() {
		return objectID;
	}

	public void setObjectID(int objectID) {
		this.objectID = objectID;
	}
}
