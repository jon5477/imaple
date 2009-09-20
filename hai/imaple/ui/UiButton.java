package imaple.ui;

//~--- non-JDK imports --------------------------------------------------------

//~--- JDK imports ------------------------------------------------------------

import imaple.util.MathUtil;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Represents a button;
 * @author David
 */
public class UiButton extends AbstractComponent {
    public static final int DISABLED = 3;
    public static final int NORMAL = 0;
    public static final int PRESSED = 2;
    public static final int ROLLOVER = 1;
    protected boolean clicked = false;
    protected UiActionListener enterListener = null;
    protected long mouseLastMove = Long.MAX_VALUE;
    protected UiImageHolder buttonImages;
    protected Image current;
    protected boolean pressed;
    protected int state;

    protected UiButton() {}

    public UiButton(Image[] image, UIManager man) {
        if (image.length != 4) {
            throw new IllegalArgumentException("Image array length was not right.");
        }

        manager = man;
        buttonImages = UiImageHolder.createFromArray(image);
        width = image[0].getWidth(null);
        height = image[0].getHeight(null);
        enabled = true;
        state = NORMAL;
        area = new Rectangle(0, 0, width, height);
        current = buttonImages.getImage(UiImageHolder.NORMAL);
        pressed = false;
    }

    public UiButton(UiImageHolder image, UIManager man) {
        manager = man;
        buttonImages = image;
        width = buttonImages.getImage(UiImageHolder.NORMAL).getWidth(null);
        height = buttonImages.getImage(UiImageHolder.NORMAL).getHeight(null);
        enabled = true;
        state = NORMAL;
        area = new Rectangle(0, 0, width, height);
        current = buttonImages.getImage(UiImageHolder.NORMAL);
        pressed = false;
    }

    public UiButton(Image image, int nwidth, int nheight, UIManager man) {
        manager = man;

        Image b[] = new Image[4];

        b[0] = image;
        b[1] = image;
        b[2] = image;
        b[3] = image;
        buttonImages = UiImageHolder.createFromArray(b);
        width = nwidth;
        height = nheight;

        if (width == -1) {
            width = buttonImages.getImage(UiImageHolder.NORMAL).getWidth(null);
        }

        if (height == -1) {
            height = buttonImages.getImage(UiImageHolder.NORMAL).getHeight(null);
        }

        enabled = true;
        state = NORMAL;
        area = new Rectangle(0, 0, width, height);
        alpha = 1;
        buttonImages.getImage(UiImageHolder.NORMAL);
    }

    public UiButton(Image[] image, int nwidth, int nheight, UIManager man) {
        if (image.length != 4) {
            throw new IllegalArgumentException("Image array length was not right.");
        }

        manager = man;
        buttonImages = UiImageHolder.createFromArray(image);
        width = image[0].getWidth(null);
        height = image[0].getHeight(null);

        /*
         * if (width == -1)
         *   width = buttonImages.getImage(UiImageHolder.NORMAL).getWidth(null);
         *       else
         *               width = nwidth;
         * if (height == -1)
         *   height = buttonImages.getImage(UiImageHolder.NORMAL).getHeight(null);
         *       else
         *               height = nheight;
         */
        enabled = true;
        state = NORMAL;
        area = new Rectangle(0, 0, width, height);
        current = buttonImages.getImage(UiImageHolder.NORMAL);
        pressed = false;
    }

    @Override
    public void onMousePressed(MouseEvent me) {
        if (!enabled) {
            return;
        }

        if (me.getButton() == MouseEvent.BUTTON1) {
            if (MathUtil.inRect(me.getX(), me.getY(), x, y, x + width, y + height)) {
                current = buttonImages.getImage(UiImageHolder.PRESSED);
                pressed = true;
                clicked = true;
            }
        }
    }

    @Override
    public void onMouseReleased(MouseEvent me) {
        if (!enabled) {
            return;
        }

        pressed = false;

        if (me.getButton() == MouseEvent.BUTTON1) {
            if (MathUtil.inRect(me.getX(), me.getY(), x, y, x + width, y + height) && clicked) {
                if (getActionListener() != null) {
                    getActionListener().actionPerformed(new UiActionEvent() {}
                    );
                }

                current = buttonImages.getImage(UiImageHolder.ROLLOVER);
            } else {
                current = buttonImages.getImage(UiImageHolder.NORMAL);
            }
        }

        clicked = false;
    }

    @Override
    public void onKeyPressed(KeyEvent key) {
        if (!enabled) {
            return;
        }

        if (key.getKeyCode() == KeyEvent.VK_ENTER) {
            if (enterListener != null) {
                enterListener.actionPerformed(new UiActionEvent());
            }
        }
    }

    @Override
    public void onMouseMoved(MouseEvent me) {
        if (!enabled) {
            return;
        }

        if (pressed) {
            return;
        }

        if (MathUtil.inRect(me.getX(), me.getY(), x, y, x + width, y + height)) {
            current = buttonImages.getImage(UiImageHolder.ROLLOVER);

            // mouseLastMove = System.currentTimeMillis();
        } else {
            current = buttonImages.getImage(UiImageHolder.NORMAL);

            // mouseLastMove = Long.MAX_VALUE;
        }
    }

    @Override
    public void setEnabled(boolean b) {
        enabled = b;

        if (b == false) {
            current = buttonImages.getImage(UiImageHolder.DISABLED);
        } else {
            current = buttonImages.getImage(UiImageHolder.NORMAL);
        }
    }

    public void addEnterListener(UiActionListener listener) {
        enterListener = listener;
    }

    public void removeEnterListener() {
        enterListener = null;
    }

    @Override
    public void render(Graphics2D g) {
        /*if ((asX != -1) && (asY != -1) && (parent != null)) {
            x = parent.getX() + asX;
            y = parent.getY() + asY;
        }*/

        if (!enabled) {
            current = buttonImages.getImage(UiImageHolder.DISABLED);
        }

        int type = AlphaComposite.SRC_OVER;
        AlphaComposite composite = AlphaComposite.getInstance(type, alpha);

        g.setComposite(composite);
        g.drawImage(current, x, y, width, height, null);

        /*
         * if (this.getToolTip() != null && !this.getToolTip().equals("") && this.isEnabled()) {
         *       if (System.currentTimeMillis() - mouseLastMove > 1000) {
         *               UIManager.drawTooltip(
         *                               g, Console.getCurrentMouseX(), Console.getCurrentMouseY() + 18, this.getToolTip());
         *       }
         * }
         */
    }
}
