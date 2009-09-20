package imaple.util;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Math routines...
 * @author David
 */
public final class MathUtil {
    private MathUtil() {}

    public static double getDistance(Point from, Point to) {
        long a = Math.abs(from.x - to.x);
        long b = Math.abs(from.y - to.y);

        return Math.sqrt((a * a + b * b));
    }

    public static double getDistance(int x, int y, int tx, int ty) {
        long a = Math.abs(x - tx);
        long b = Math.abs(y - ty);

        return Math.sqrt((a * a + b * b));
    }

    public static boolean isNegative(int test) {
        if (test < 0) {
            return true;
        }

        return false;
    }

    public static boolean inRect(int x, int y, int sx, int sy, int ex, int ey) {
        if ((x > sx) && (x < ex) && (y > sy) && (y < ey)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean inRect(int x, int y, Rectangle r) {
        if ((x > r.x) && (x < r.x + r.width) && (y > r.y) && (y < r.y + r.height)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean inRect(Point pt, int sx, int sy, int ex, int ey) {
        int x = pt.x;
        int y = pt.y;

        if ((x > sx) && (x < ex) && (y > sy) && (y < ey)) {
            return true;
        } else {
            return false;
        }
    }
	
	/**
	 * Returns whether an object is an instance of a class. This should be faster than instanceof.
	 * @param obj Object to test
	 * @param cls Class to test with
	 * @return true if an instance, false if not
	 */
	public static boolean instanceOf(Object obj, Class<?> cls) {
		while (cls != null) {
			if (obj.getClass().getName().equals(cls.getName())) {
				return true;
			}
			cls = cls.getSuperclass();
		}
		return false;
	}
	
	public static boolean checkPixelCollision(Image src_, Image dst_, int startX, int startY, int checkWidth,
			int checkHeight) {
		BufferedImage src = (BufferedImage) src_;
		BufferedImage dst = (BufferedImage) dst_;
		for (int i = startX; i < checkWidth; i++) {
			for (int j = startY; j < checkHeight; j++) {
				int rgbaSRC = src.getRGB(i, j);
				int rgbaDST = dst.getRGB(i, j);
				if (((rgbaSRC >> 24) & 0xFF) != 0x00 &&
						(((rgbaDST) >> 24) & 0xFF) != 0x00) {
					return true;
				}
			}
		}
		return false;
	}
}
