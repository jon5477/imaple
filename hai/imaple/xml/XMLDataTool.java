/*
 * Haiiiiiiii faggot
 */
package imaple.xml;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author David
 */
public class XMLDataTool {
    private XMLDataTool() {}

    public static String getString(XMLData data, boolean returnNonNull) {
        try {
            return ((String) data.getData());
        } catch (NullPointerException ee) {
            if (returnNonNull) {
                return "";
            } else {
                throw ee;
            }
        }
    }

    public static String getString(XMLData data) {
        return getString(data, true);
    }

    public static String getString(XMLData data, String def) {
        if ((data == null) || (data.getData() == null)) {
            return def;
        } else {
            return ((String) data.getData());
        }
    }

    public static String getString(String path, XMLData data) {
        return getString(data.getChildByPath(path));
    }

    public static String getString(String path, XMLData data, boolean returnNonNull) {
        return getString(data.getChildByPath(path), returnNonNull);
    }

    public static String getString(String path, XMLData data, String def) {
        try {
            return getString(data.getChildByPath(path), def);
        } catch (NullPointerException ee) {
            return "";
        }
    }

    public static double getDouble(XMLData data) {
        return ((Double) data.getData()).doubleValue();
    }

    public static float getFloat(XMLData data) {
        return ((Float) data.getData()).floatValue();
    }

    public static int getInt(XMLData data) {
        return ((Integer) data.getData()).intValue();
    }

    public static int getInt(XMLData data, int def) {
        if ((data == null) || (data.getData() == null)) {
            return def;
        } else {
            return ((Integer) data.getData()).intValue();
        }
    }

    public static int getInt(String path, XMLData data) {
        return getInt(data.getChildByPath(path));
    }

    public static int getIntConvert(XMLData data) {
        if (data.getType() == XMLDataType.STRING) {
            return Integer.parseInt(getString(data));
        } else {
            return getInt(data);
        }
    }

    public static int getIntConvert(String path, XMLData data) {
        XMLData d = data.getChildByPath(path);

        if (d.getType() == XMLDataType.STRING) {
            return Integer.parseInt(getString(d));
        } else {
            return getInt(d);
        }
    }

    public static float getFloatConvert(String path, XMLData data) {
        XMLData d = data.getChildByPath(path);

        if (d.getType() == XMLDataType.STRING) {
            return Float.parseFloat(getString(d));
        } else {
            return getFloat(d);
        }
    }

    public static int getInt(String path, XMLData data, int def) {
        return getInt(data.getChildByPath(path), def);
    }

    public static int getIntConvert(String path, XMLData data, int def) {
        XMLData d = data.getChildByPath(path);

        if (d == null) {
            return def;
        }

        if (d.getType() == XMLDataType.STRING) {
            try {
                return Integer.parseInt(getString(d));
            } catch (NumberFormatException nfe) {
                return def;
            }
        } else {
            return getInt(d, def);
        }
    }

    public static BufferedImage getImage(XMLData data) {
        return null;
    }

    public static Point getPoint(XMLData data) {
        return ((Point) data.getData());
    }

    public static Point getPoint(String path, XMLData data) {
        return getPoint(data.getChildByPath(path));
    }

    public static Point getPoint(String path, XMLData data, Point def) {
        final XMLData pointData = data.getChildByPath(path);

        if (pointData == null) {
            return def;
        }

        return getPoint(pointData);
    }

    public static String getFullDataPath(XMLData data) {
        String path = "";
        DataEntity myData = data;

        while (myData != null) {
            path = myData.getName() + "/" + path;
            myData = myData.getParent();
        }

        return path.substring(0, path.length() - 1);
    }
}
