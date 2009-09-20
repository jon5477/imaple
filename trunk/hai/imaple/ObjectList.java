/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Object List - Used to maintain lists of current objects.
 * This class is thread-safe.
 * For more information on threading, see
 * @see java.lang.Thread
 * @author David
 */
public class ObjectList {
	private final List<GameObject> objs =
			Collections.synchronizedList(new ArrayList());
	private final Object objLock = new Object();

	public void addObject(GameObject obj) {
		synchronized (objLock) {
			objs.add(obj);
		}
	}
	
	public void removeObject(GameObject obj) {
		synchronized (objLock) {
			objs.remove(obj);
		}
	}

	public int size() {
		synchronized (objLock) {
			return objs.size();
		}
	}

	public void clear() {
		synchronized (objLock) {
			objs.clear();
		}
	}

	public List<GameObject> getObjList() {
		synchronized (objLock) {
			return objs;
		}
	}

	public Iterator<GameObject> iterator() {
		synchronized (objLock) {
			return objs.iterator();
		}
	}
}
