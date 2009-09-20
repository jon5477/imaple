/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imaple.ui;

//~--- non-JDK imports --------------------------------------------------------


import java.awt.Image;

/**
 *
 * @author David
 */
public class UiImageHolder {
    public static final int
        NORMAL = 0, ROLLOVER = 1, PRESSED = 2, DISABLED = 3;
    private Image normal, rollover, pressed, disabled;

    public UiImageHolder(Image normal, Image rollover, Image pressed) {
        this(normal, rollover, pressed, null);
    }

    public UiImageHolder(Image normal, Image rollover, Image pressed, Image disabled) {
        this.normal = normal;
        this.rollover = rollover;
        this.pressed = pressed;
        this.disabled = disabled;
    }

    public Image getDisabled() {
        return disabled;
    }

    public Image getNormal() {
        return normal;
    }

    public Image getPressed() {
        return pressed;
    }

    public Image getRollover() {
        return rollover;
    }

    public Image getImage(int which) {
        switch (which) {
        case NORMAL :
            return normal;

        case ROLLOVER :
            return rollover;

        case PRESSED :
            return pressed;

        case DISABLED :
            return disabled;

        default :
            throw new RuntimeException("Fail much?");
        }
    }

    public static UiImageHolder createFromArray(Image[] images) {
        return new UiImageHolder(images[0], images[1], images[2], (images.length >= 4)
                ? images[3]
                : null);
    }
}
