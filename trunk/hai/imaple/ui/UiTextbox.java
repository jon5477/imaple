package imaple.ui;

//~--- non-JDK imports --------------------------------------------------------

//~--- JDK imports ------------------------------------------------------------

import imaple.util.ExtendedGraphics;
import imaple.util.MathUtil;
import imaple.util.StringManipulator;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Represents a textbox.
 * @author David
 */
public class UiTextbox extends UiTextComponent {
    protected boolean active = false;
    protected int currentScroll = 0;
    protected int cursorHeight = 0;
    protected int cursorPos = 0;
    protected byte cursorWidth = 1;
    protected UiActionListener downArrowListener = null;
    protected char echoChar = (char) -1;
    protected int maxLength = 40;
    protected int repeatRate = 50 / 3;
    protected StringBuilder text = new StringBuilder();
    protected Color selectColor = Color.BLUE;
    protected Color fontColor = Color.BLACK;
    protected Font font;
    protected Color cursorColor = Color.BLACK;
    protected int textX = 2, textY = 2;
    protected boolean selecting = false;
    protected long lastType = System.currentTimeMillis();
    protected long lastFlash = System.currentTimeMillis();
    protected boolean flash = true;
    protected boolean allowCopyPaste = true;
    protected boolean typing = false;
    protected boolean drawBox = true;
    protected UiActionListener upArrowListener = null;
    protected boolean onlyAllowNumbers = false;
    protected FontMetrics fm;
    protected Image left, right, center;
    protected int selectAnchor, selectEnd;

    protected UiTextbox() {}

    public UiTextbox(int width, int height, UIManager man) {
        this(null, null, null, width, height, man);
        this.drawBox = false;
    }

    public UiTextbox(Image S_left, Image S_right, Image S_center, int S_width, int S_height, UIManager man) {
        this(S_left, S_right, S_center, S_width, S_height, -1, "", man);
    }

    public UiTextbox(Image S_left, Image S_right, Image S_center, int S_width, int S_height, int S_maxLength,
                     UIManager man) {
        this(S_left, S_right, S_center, S_width, S_height, S_maxLength, "", man);
    }

    public UiTextbox(Image S_left, Image S_right, Image S_center, int S_width, int S_height, int S_maxLength,
                     String S_text, UIManager man) {
        width = S_width;
        height = S_height;
        selectAnchor = 0;
        selectEnd = 0;
        text = new StringBuilder(S_text);
        font = null;
        maxLength = S_maxLength;
        active = false;
        left = S_left;
        right = S_right;
        center = S_center;
		font = new Font("Impact", Font.PLAIN, 18);
        if ((left == null) || (right == null) || (center == null)) {
            this.drawBox = false;
        }

        alpha = 1;
        enabled = true;
        editable = true;
        manager = man;
        this.maxLength = 80;

        // undo = new ArrayList<String>();
        // cursor = new ArrayList<Integer>();
    }

    @Override
    public void render(Graphics2D g) {

        try {
            if (currentScroll < 0) {
                System.err.println("Wtf?");
                currentScroll = 0;
            }

            int type = AlphaComposite.SRC_OVER;
            AlphaComposite composite = AlphaComposite.getInstance(type, alpha);

            g.setComposite(composite);

            int take = left.getWidth(null) + right.getWidth(null);
            int nwidth = width - take;

            // Draw body
            if (drawBox) {
                g.drawImage(left, x, y, left.getWidth(null), height, null);
                g.drawImage(right, x + width - right.getWidth(null), y, right.getWidth(null), height, null);
                g.drawImage(center, x + left.getWidth(null), y, nwidth, height, null);
            }

            if (font != null) {
                g.setFont(font);
            }

            fm = g.getFontMetrics(font);

            composite = AlphaComposite.getInstance(type, 1.0f);
            g.setComposite(composite);
            int l = getSelectedText().length();
            int ll = 0;
			int currX = 0;

            // Draw text
            for (int i = currentScroll; i < text.length(); i++) {
                char c = text.charAt(i);

                if (echoChar != (char) -1) {
                    c = echoChar;
                }

                if ((((i >= selectAnchor) && (i <= selectEnd) && (selectAnchor < selectEnd))
                        || ((i <= selectAnchor) && (i >= selectEnd) && (selectAnchor > selectEnd))) &&!(selectAnchor
                           == selectEnd) && (ll < l)) {
                    g.setColor(selectColor);
                    fm = g.getFontMetrics(font);

                    // if (fm.stringWidth(currentDrawn) >= width) break;
                    int len = fm.charWidth(c);
                    g.fillRect(x + currX + textX, y + textY, len, height - textY);
                    g.setColor(fontColor);
                    g.drawString(String.valueOf(c), x + textX + currX, y + textY + 15);
                    ll++;
                } else {
                    fm = g.getFontMetrics(font);
                    g.setColor(fontColor);
                    g.drawString(String.valueOf(c), x + textX + currX, y + textY + 15);
                }
				currX += fm.charWidth(c);
				if (currX >= width) {
					break;
				}
            }

            // Draw Cursor
            if ((active && flash && (selectAnchor == selectEnd) && editable && enabled)
                    || (typing && editable && enabled)) {
                fm = g.getFontMetrics(font);

				String cursorTxt = text.substring(currentScroll, cursorPos);

                if (echoChar != (char) -1) {
                    char[] a = new char[Math.abs(currentScroll - cursorPos)];

                    for (int i = 0; i < Math.abs(currentScroll - cursorPos); i++) {
                        a[i] = echoChar;
                    }

                    cursorTxt = new String(a);
                }

                int cursorX = fm.stringWidth(cursorTxt);

                g.setColor(cursorColor);
                ExtendedGraphics.drawThickLine(x + textX + cursorX, y + textY + cursorHeight, x + textX + cursorX,
                                               y + height - textY - cursorHeight, cursorWidth, g, cursorColor);
            }

            if (flash && (System.currentTimeMillis() - lastFlash > 500)) {
                flash = false;
                lastFlash = System.currentTimeMillis();
            } else if (System.currentTimeMillis() - lastFlash > 500) {
                flash = true;
                lastFlash = System.currentTimeMillis();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    protected void scrollToCursor(boolean force) {
		int xpos = 0;
		int xW = fm.stringWidth(text.substring(0, cursorPos));
		while (xW > 0) {
			xW -= fm.charWidth(text.charAt(xpos++));
		}
		int renderWidth = 0;
		int rW = width - 5;
		while (rW > 0) {
			if (renderWidth +  this.currentScroll >= text.length()) {
				break;
			}
			rW -= fm.charWidth(text.charAt((renderWidth++) +  this.currentScroll));
		}
        if(xpos < this.currentScroll + 5) {
             this.currentScroll = Math.max(0, xpos - 9);
        } else if(force || xpos -  this.currentScroll > renderWidth) {
            this.currentScroll = Math.max(0, xpos - renderWidth);
        }
    }

    protected void selectWordFromMouse(int index) {
        this.selectAnchor = index;
        this.selectEnd = index;

        while ((selectAnchor > 0) &&!Character.isWhitespace(text.charAt(selectAnchor - 1))) {
            selectAnchor--;
        }

        while ((selectEnd < text.length()) &&!Character.isWhitespace(text.charAt(selectEnd))) {
            selectEnd++;
        }
    }

    /**
     * Handles keys.
     * @param key
     */
    @Override
    public void onKeyPressed(KeyEvent key) {
        if (!enabled) {
            return;
        }

        if (System.currentTimeMillis() - lastType < repeatRate) {
            return;
        }

        if (!active) {
            return;
        }

        lastType = System.currentTimeMillis();

        int keyid = key.getKeyCode();

        if (key.isControlDown()) {
            if ((keyid == KeyEvent.VK_C) && allowCopyPaste) {

                // Copy ;)
                if ((getSelectedText() != null) &&!getSelectedText().equals("")) {
                    manager.getClipboardManipulator().setClipboardContents(getSelectedText());
                }
            } else if ((keyid == KeyEvent.VK_X) && allowCopyPaste) {
                if (!editable) {
                    return;
                }

                // Cut ;)
                if ((getSelectedText() != null) &&!getSelectedText().equals("")) {

                    // undo.add(text);
                    // cursor.add(cursorPos);
                    manager.getClipboardManipulator().setClipboardContents(getSelectedText());
                    deleteSelection();
                }
            } else if ((keyid == KeyEvent.VK_V) && allowCopyPaste) {
                if (!editable) {
                    return;
                }

                // Paste ;)
                // undo.add(text);
                // undoIndex++;
                if ((getSelectedText() != null) && !getSelectedText().equals("")) {
                    deleteSelection();
                }
                String s = manager.getClipboardManipulator().getClipboardContents();

                for (int i = 0; (i < s.length()) && (text.length() < this.maxLength); i++) {
                    text.insert(cursorPos, s.charAt(s.length() - i - 1));
                    cursorPos++;
                }

                shiftToCursor();
            } else if (keyid == KeyEvent.VK_A) {

                // Select all ;)
                selectAnchor = 0;
                selectEnd = text.length();
                cursorPos = text.length();
            } else if (keyid == KeyEvent.VK_Z) {
                if (!editable) {
                    return;
                }

                // Undo ;)
                try {

                    // cursorPos = cursor.get(undoIndex - 1);
                    // text = undo.get(undoIndex - 1);
                    // undo.remove(undoIndex - 1);
                    // undoIndex--;
                } catch (Exception exc) {
                    System.out.println("Can't undo.");
                }
            } else if (keyid == KeyEvent.VK_Y) {
                if (!editable) {
                    return;
                }

                // Redo ;)
            }
        } else {
            if (keyid == KeyEvent.VK_DELETE) {
                if (!editable) {
                    return;
                }

                // cursor.add(cursorPos);
                // undo.add(text);
                // undoIndex++;
                deleteSelection();
            } else if (keyid == KeyEvent.VK_ENTER) {
                if (!editable) {
                    return;
                }

                if (!enabled) {
                    return;
                }

                if (getActionListener() != null) {
                    getActionListener().actionPerformed(new UiActionEvent() {}
                    );
                }

                return;
            } else if (keyid == KeyEvent.VK_TAB) {
                if (!enabled) {
                    return;
                }

                if (getTabListener() != null) {
                    getTabListener().actionPerformed(new UiActionEvent());
                }

                return;
            } else if (keyid == KeyEvent.VK_END) {
                shiftCursor(text.length());
            } else if (keyid == KeyEvent.VK_HOME) {
                shiftCursor(0);
                currentScroll = 0;
            } else if (keyid == KeyEvent.VK_LEFT) {
                if (!key.isShiftDown()) {
                    shiftCursor(Math.max(cursorPos - 1, 0));
                    // resetSelection();
                } else {
                    selectEnd = Math.max(0, selectEnd - 1);
                    cursorPos = Math.max(0, selectEnd);
                }
            } else if (keyid == KeyEvent.VK_RIGHT) {
                if (!key.isShiftDown()) {
                    shiftCursor(Math.min(cursorPos + 1, text.length()));
                    // resetSelection();
                } else {
                    selectEnd = Math.min(text.length(), selectEnd + 1);
                    cursorPos = Math.min(text.length(), selectEnd);
                    selectEnd = cursorPos;
                }
            } else if (keyid == KeyEvent.VK_BACK_SPACE) {
                if (!editable) {
                    return;
                }

                // cursor.add(cursorPos);
                // undo.add(text);
                // undoIndex++;
                if (selectAnchor == selectEnd) {
                    if (cursorPos > 0) {
                        text.deleteCharAt(cursorPos - 1);
                        shiftCursor(cursorPos - 1);
                    }
                } else {
                    deleteSelection();
                }
            } else if (keyid == KeyEvent.VK_ESCAPE) {}
            else if (keyid == KeyEvent.VK_UP) {
                if (upArrowListener != null) {
                    upArrowListener.actionPerformed(new UiActionEvent());
                }
            } else if (keyid == KeyEvent.VK_DOWN) {
                if (downArrowListener != null) {
                    downArrowListener.actionPerformed(new UiActionEvent());
                }
            } else {
                if (!editable) {
                    return;
                }

                if (text.length() >= maxLength) {
                    return;
                }

                if ((selectAnchor == selectEnd) && (int) key.getKeyChar() >= 0 && (int) key.getKeyChar() <= 255) {

                    // cursor.add(cursorPos);
                    // undo.add(text);
                    // undoIndex++;
                    if (key.isShiftDown()) {
                        if (!onlyAllowNumbers
                                || ((int) key.getKeyChar() >= (int) '0' && (int) key.getKeyChar() <= (int) '9')) {
                            text.insert(cursorPos, StringManipulator.toUpperCase(key.getKeyChar()));
                            shiftCursor(cursorPos + 1);
                        }
                    } else {
                        if (!onlyAllowNumbers
                                || ((int) key.getKeyChar() >= (int) '0' && (int) key.getKeyChar() <= (int) '9')) {
                            text.insert(cursorPos, key.getKeyChar());
                            shiftCursor(cursorPos + 1);
                        }
                    }
                } else if ((int) key.getKeyChar() >= 0 && (int) key.getKeyChar() <= 255) {

                    // cursor.add(cursorPos);
                    // undo.add(text);
                    // undoIndex++;
                    deleteSelection();

                    if (key.isShiftDown()) {
                        if (!onlyAllowNumbers
                                || ((int) key.getKeyChar() >= (int) '0' && (int) key.getKeyChar() <= (int) '9')) {
                            text.insert(cursorPos, StringManipulator.toUpperCase(key.getKeyChar()));
                            shiftCursor(cursorPos + 1);
                        }
                    } else {
                        if (!onlyAllowNumbers
                                || ((int) key.getKeyChar() >= (int) '0' && (int) key.getKeyChar() <= (int) '9')) {
                            text.insert(cursorPos, key.getKeyChar());
                            shiftCursor(cursorPos + 1);
                        }
                    }
                }
            }
        }

        typing = true;
		this.scrollToCursor(true);

        if (this.getTextListener() != null) {
            this.getTextListener().actionPerformed(new UiActionEvent());
        }
    }

    @Override
    public void onKeyReleased(KeyEvent key) {
        typing = false;
    }

    public void resetScroll() {
        this.currentScroll = 0;
    }

    public void shiftCursor(int where) {
        where = Math.max(0, where);
        cursorPos = where;
        selectAnchor = where;
        selectEnd = where;
    }

    protected void shiftToCursor() {
        selectAnchor = cursorPos;
        selectEnd = cursorPos;
    }

    @Override
    public void onMousePressed(MouseEvent me) {
        if (!enabled) {
            return;
        }

        if (!MathUtil.inRect(me.getX(), me.getY(), x, y, x + width, y + height)) {
            active = false;
            selecting = false;

            // System.out.println("nah");
        } else {
            selecting = true;
            active = true;

            int chrWidthTotal = textX, totalChars = 0, chrWidth;
            char chr;

            for (int i = currentScroll; i < text.length(); i++) {
                chr = text.charAt(i);

                if (echoChar != (char) -1) {
                    chr = echoChar;
                }

                chrWidth = fm.charWidth(chr);
                chrWidthTotal += chrWidth;
                totalChars += 1;

                if (me.getX() - x >= chrWidthTotal - 2) {
                    selectEnd = totalChars + currentScroll;
                    selectAnchor = selectEnd;
                    cursorPos = selectEnd;
                }
            }

            // System.out.println("yah");
        }
    }

    @Override
    public void onMouseReleased(MouseEvent me) {
        if (!enabled) {
            return;
        }

        if (!MathUtil.inRect(me.getX(), me.getY(), x, y, x + width, y + height) &&!selecting) {
            active = false;
            selecting = false;

            // System.out.println("nah");
        } else if (me.getClickCount() == 2) {
            this.selectWordFromMouse(cursorPos);
        } else if (me.getClickCount() == 3) {
            selectAnchor = 0;
            selectEnd = text.length();
            cursorPos = text.length();
        } else if (selecting) {
            selecting = false;
            active = true;

            // System.out.println("yah");
        } else {
            selecting = false;
            active = true;
        }
    }

    @Override
    public void onMouseMoved(MouseEvent me) {
        if (!enabled) {
            return;
        }

        if (selecting) {
            int chrWidthTotal = textX, totalChars = 0, chrWidth;
            char chr;

            for (int i = currentScroll; i < text.length(); i++) {
                chr = text.charAt(i);

                if (echoChar != (char) -1) {
                    chr = echoChar;
                }

                // if (chr == 'U') System.out.println(i);
                chrWidth = fm.charWidth(chr);
                chrWidthTotal += chrWidth;
                totalChars += 1;

                if (me.getX() - x >= chrWidthTotal - 2) {
                    selectEnd = totalChars + currentScroll;
                    cursorPos = selectEnd;
                }

                if (me.getX() - x < 0) {
                    selectEnd = currentScroll;
                    cursorPos = selectEnd;
                }
            }
        }
    }

    @Override
    public void onMouseDragged(MouseEvent me) {
        onMouseMoved(me);
    }

    protected void deleteSelection() {
        if (selectAnchor == selectEnd) {
            return;
        }

        int start, end;

        start = selectAnchor;
        end = selectEnd;

        if (selectAnchor > selectEnd) {
            start = selectEnd;
            end = selectAnchor;
        }

        // currentScroll = Math.abs(start - cursorPos);
        currentScroll = Math.max(0, cursorPos - getSelectedText().length());
        cursorPos = start;
        text.delete(start, end);
        selectAnchor = cursorPos;
        selectEnd = cursorPos;
    }

    protected void resetSelection() {
        selectAnchor = cursorPos;
        selectEnd = cursorPos;
    }

    /*
     * @Override public void onMouseClicked(MouseEvent me) {
     *   if (!enabled) return;
     *   if (manager.getFocus() != null) {
     *       if (manager.getFocus() != this.parent) {
     *           return;
     *       }
     *   }
     *   if (!MathUtil.inRect(me.getX(), me.getY(), x, y, x + width, y + height) && !selecting) {
     *       active = false;
     *       selecting = false;
     *       //System.out.println("nah");
     *   } else if (me.getClickCount() == 2) {
     *       this.selectWordFromMouse(cursorPos);
     *   } else if (me.getClickCount() == 3) {
     *       selectAnchor = 0;
     *       selectEnd = text.length();
     *       cursorPos = text.length();
     *   } else if (selecting) {
     *       selecting = false;
     *       active = true;
     *       //System.out.println("yah");
     *   } else {
     *       selecting = false;
     *       active = true;
     *   }
     * }
     */
    public void setText(String newText) {
        text = new StringBuilder(newText);
    }

    public String getText() {
        return text.toString();
    }

    public String getSelectedText() {
        if (selectAnchor == selectEnd) {
            return "";
        }

        int start, end;

        start = selectAnchor;
        end = selectEnd;

        if (selectAnchor > selectEnd) {
            start = selectEnd;
            end = selectAnchor;
        }

        return text.substring(start, end);
    }

    public void setEchoCharacter(char echo) {
        echoChar = echo;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font fnt) {
        font = fnt;
    }

    public void setTextPosition(Point pos) {
        textX = pos.x;
        textY = pos.y;
    }

    public void setCursorColor(Color c) {
        cursorColor = c;
    }

    public Color getCursorColor() {
        return cursorColor;
    }

    public void setSelectColor(Color c) {
        selectColor = c;
    }

    public Color getSelectColor() {
        return selectColor;
    }

    public void setFontColor(Color c) {
        fontColor = c;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setEchoChar(char c) {
        echoChar = c;
    }

    public char getEchoChar() {
        return echoChar;
    }

    public byte getCursorWidth() {
        return cursorWidth;
    }

    public void setCursorWidth(byte newf) {
        cursorWidth = newf;
    }

    public Point getTextPosition() {
        return new Point(textX, textY);
    }

    public void setTextPosition(int xxx, int yyy) {
        textX = xxx;
        textY = yyy;
    }

    public boolean getCopyPaste() {
        return allowCopyPaste;
    }

    public void setCopyPaste(boolean yn) {
        allowCopyPaste = yn;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int m) {
        maxLength = m;
    }

    public void setActive(boolean a) {
        active = a;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isDrawBox() {
        return drawBox;
    }

    public int getCursorHeight() {
        return cursorHeight;
    }

    public void setCursorHeight(int cursorHeight) {
        this.cursorHeight = cursorHeight;
    }

    public void setDrawBox(boolean drawBox) {
        this.drawBox = drawBox;
    }

    public void addDownArrowListener(UiActionListener listener) {
        downArrowListener = listener;
    }

    public void addUpArrowListener(UiActionListener listener) {
        upArrowListener = listener;
    }

    public boolean isOnlyAllowNumbers() {
        return onlyAllowNumbers;
    }

    public void setOnlyAllowNumbers(boolean onlyAllowNumbers) {
        this.onlyAllowNumbers = onlyAllowNumbers;
    }
}
