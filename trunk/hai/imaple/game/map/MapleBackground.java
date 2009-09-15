/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.game.map;

import imaple.Core;
import imaple.Core.DataFileType;
import imaple.ImageFactory;
import java.awt.Image;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.zip.ZipFile;

/**
 *
 * @author Haiku01
 */

public class MapleBackground {
    public final HashMap<Integer, Image> frames = new LinkedHashMap<Integer, Image>();

    public int numOfFrames = 0;

    public void loadBackground(DataFileType map, String backgroundName) {
        ZipFile mapData = Core.data.get(map);
        while (mapData.entries().hasMoreElements()) { // Cache the map content?
            frames.put(frames.size() + 1, ImageFactory.loadImage(map, mapData.entries().nextElement().getName()));
        }
        numOfFrames = frames.size();
    }

    public void clearBackgrounds() {
        frames.clear();
    }
}
