/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.network;

import imaple.network.input.SeekableLittleEndianAccessor;

/**
 *
 * @author David
 */
public interface JPacketHandler {
	void handle(SeekableLittleEndianAccessor slea);
}
