package imaple.ui;

//~--- non-JDK imports --------------------------------------------------------

/**
 *
 * @author David
 */
public abstract class UiTextComponent extends AbstractComponent {
    protected boolean editable;
    protected UiActionListener tabListener;
    protected UiActionListener textListener;

    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean e) {
        editable = e;
    }

    public UiActionListener getTabListener() {
        return tabListener;
    }

    public void addTabListener(UiActionListener tabListener) {
        this.tabListener = tabListener;
    }

    public UiActionListener getTextListener() {
        return textListener;
    }

    public void setTextListener(UiActionListener textListener) {
        this.textListener = textListener;
    }
}
