package imaple.ui;

//~--- non-JDK imports --------------------------------------------------------

import imaple.GameObject;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Dimension;
import java.awt.event.MouseEvent;

/**
 * All GUI Components should extend this class.
 * @author David
 */
public abstract class AbstractComponent extends GameObject {
    protected float alpha = 1.0f;
    protected boolean enabled = true;
    protected UiActionListener listener = null;
    protected boolean showTooltip = false;
    protected int asX = -1, asY = -1;
    protected static UIManager manager;
    protected long mouseHasNotMovedFor;
    protected String tooltip;
    protected int width, height;

    public void setAlpha(float al) {
        if (al < 0) {
            al = 0;
        }

        if (al > 1) {
            al = 1;
        }

        alpha = al;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setToolTip(String s) {
        tooltip = s;
    }

    public String getToolTip() {
        return tooltip;
    }

    public void setEnabled(boolean enable) {
        enabled = enable;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int w) {
        width = w;
    }

    public void setHeight(int h) {
        height = h;
    }

    public void setSize(Dimension d) {
        height = d.height;
        width = d.width;
    }

    public void addActionListener(UiActionListener listen) {
        listener = listen;
    }

    public void removeActionListener() {
        listener = null;
    }

    public UiActionListener getActionListener() {
        return listener;
    }

    public static UIManager getUiManager() {
        return manager;
    }

    public static void setUiManager(UIManager man) {
        manager = man;
    }

    public int getAsX() {
        return asX;
    }

    public void setAsX(int asX) {
        this.asX = asX;
    }

    public int getAsY() {
        return asY;
    }

    public void setAsY(int asY) {
        this.asY = asY;
    }

    public long getMouseHasNotMovedFor() {
        return mouseHasNotMovedFor;
    }

    public void setMouseHasNotMovedFor(long mouseHasNotMovedFor) {
        this.mouseHasNotMovedFor = mouseHasNotMovedFor;
    }

    public void checkTooltip(MouseEvent me) {}
}
