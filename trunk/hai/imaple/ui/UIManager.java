package imaple.ui;


/**
 * Handles UI.
 * @author David
 */
public class UIManager {
    private ClipboardManipulator clip;

    public UIManager() {
        clip = new ClipboardManipulator();
    }

    public ClipboardManipulator getClipboardManipulator() {
        return clip;
    }

    public void createButton() {}
}
