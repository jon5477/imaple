package imaple.util;
//~--- non-JDK imports --------------------------------------------------------

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

//~--- JDK imports ------------------------------------------------------------



/**
 * Extended Graphics utility...
 * @author David
 */
public final class ExtendedGraphics {
    private ExtendedGraphics() {}

    public static void drawThickRectangle(int S_x, int S_y, int S_width, int S_height, int S_thickness, Graphics g) {
        for (int i = 0; i < S_thickness; i++) {
            g.drawRect(S_x - i, S_y - i, S_width + i * 2, S_height + i * 2);
        }
    }

    public static void drawThickRectangle(int S_x, int S_y, int S_width, int S_height, int S_thickness, Graphics g,
            Color c) {
        for (int i = 0; i < S_thickness; i++) {
            g.setColor(c);
            g.drawRect(S_x - i, S_y - i, S_width + i * 2, S_height + i * 2);
        }
    }

    public static void drawThickLine(int S_x, int S_y, int S_x1, int S_y1, int S_thickness, Graphics g) {
        g.drawLine(S_x, S_y, S_x1, S_y1);

        for (int i = 0; i < S_thickness / 2; i++) {
            g.drawLine(S_x - i, S_y - i, S_x1 - i, S_y1 - i);
        }

        for (int i = 0; i < S_thickness / 2; i++) {
            g.drawLine(S_x + i, S_y + i, S_x1 + i, S_y1 + i);
        }
    }

    public static void drawThickLine(int S_x, int S_y, int S_x1, int S_y1, int S_thickness, Graphics g, Color c) {
        g.setColor(c);
        g.drawLine(S_x, S_y, S_x1, S_y1);

        for (int i = 0; i < S_thickness / 2; i++) {
            g.drawLine(S_x - i, S_y - i, S_x1 - i, S_y1 - i);
        }

        for (int i = 0; i < S_thickness / 2; i++) {
            g.drawLine(S_x + i, S_y + i, S_x1 + i, S_y1 + i);
        }
    }

    public static void drawFilledRectangle(int S_x, int S_y, int S_width, int S_height, Graphics g) {
        for (int i = 0; i < S_height; i++) {
            g.drawRect(S_x - i, S_y - i, S_width + i * 2, S_height + i * 2);
        }
    }

    public static void drawFilledRectangle(int S_x, int S_y, int S_width, int S_height, Graphics g, Color c) {
        for (int i = 0; i < S_height; i++) {
            g.setColor(c);
            g.drawRect(S_x - i, S_y - i, S_width + i * 2, S_height + i * 2);
        }
    }

    public static void drawThickEclipse(int S_x, int S_y, int S_width, int S_height, int S_thickness, Graphics g) {
        for (int i = 0; i < S_thickness; i++) {
            g.drawOval(S_x - i, S_y - i, S_width + i, S_height + i);
        }
    }

    public static void drawThickEclipse(int S_x, int S_y, int S_width, int S_height, int S_thickness, Graphics g,
            Color c) {
        for (int i = 0; i < S_thickness; i++) {
            g.setColor(c);
            g.drawOval(S_x - i, S_y - i, S_width + i, S_height + i);
        }
    }

    public static void drawFilledEclipse(int S_x, int S_y, int S_width, int S_height, Graphics g) {
        for (int i = 0; i < S_height; i++) {
            g.drawOval(S_x - i, S_y - i, S_width + i, S_height + i);
        }
    }

    public static void drawFilledEclipse(int S_x, int S_y, int S_width, int S_height, Graphics g, Color c) {
        for (int i = 0; i < S_height; i++) {
            g.setColor(c);
            g.drawOval(S_x - i, S_y - i, S_width + i, S_height + i);
        }
    }

    public static void drawHealthbar(float val, int S_x, int S_y, int S_width, int S_height, Graphics g, Color c,
                                     Color bgColor, boolean outline) {
        if ((val > 100) || (val < 0)) {
            throw new RuntimeException("Drawing Healthbar failed (Value out of range.)");
        }

        float pcent = val / 100;
        int w = (int) (S_width * pcent);

        drawFilledRectangle(S_x, S_y, S_width, S_height, g, bgColor);
        drawFilledRectangle(S_x, S_y, w, S_height, g, c);

        if (outline) {
            g.setColor(Color.BLACK);
            g.drawRect(S_x, S_y, S_width, S_height);
        }
    }

    public static void drawHealthbar(float val, int S_x, int S_y, int S_width, int S_height, Graphics g, Color c,
                                     Color bgColor, boolean outline, Color borderColor) {
        if ((val > 100) || (val < 0)) {
            throw new RuntimeException("Drawing Healthbar failed (Value out of range.)");
        }

        float pcent = val / 100;
        int w = (int) (S_width * pcent);

        drawFilledRectangle(S_x, S_y, S_width, S_height, g, bgColor);
        drawFilledRectangle(S_x, S_y, w, S_height, g, c);

        if (outline) {
            g.setColor(borderColor);
            g.drawRect(S_x, S_y, S_width, S_height);
        }
    }

    public static void drawRectangle(int x, int y, int bottomx, int bottomy, Graphics g) {
        if ((x < bottomx) && (y < bottomy)) {
            g.drawRect(x, y, bottomx - x, bottomy - y);
        } else if ((x > bottomx) && (y > bottomy)) {
            g.drawRect(bottomx, bottomy, x - bottomx, y - bottomy);
        } else if ((x > bottomx) && (y < bottomy)) {
            g.drawRect(bottomx, y, x - bottomx, bottomy - y);
        } else if ((x < bottomx) && (y > bottomy)) {
            g.drawRect(x, bottomy, bottomx - x, y - bottomy);
        }
    }

    public static void drawRectangle(int x, int y, int bottomx, int bottomy, Graphics g, Color c) {
        g.setColor(c);

        if ((x < bottomx) && (y < bottomy)) {
            g.drawRect(x, y, bottomx - x, bottomy - y);
        } else if ((x > bottomx) && (y > bottomy)) {
            g.drawRect(bottomx, bottomy, x - bottomx, y - bottomy);
        } else if ((x > bottomx) && (y < bottomy)) {
            g.drawRect(bottomx, y, x - bottomx, bottomy - y);
        } else if ((x < bottomx) && (y > bottomy)) {
            g.drawRect(x, bottomy, bottomx - x, y - bottomy);
        }
    }

    public static void drawDropShadowString(String string, int x, int y, int offx, int offy, Graphics2D g, Color fcol,
            Color bcol) {
        g.setColor(bcol);
        g.drawString(string, x, y);
        g.setColor(fcol);
        g.drawString(string, x - offx, y - offy);
    }
}
