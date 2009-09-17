/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.canvas;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author David
 */
public abstract class AbstractCanvas {
    public int getRenderDelay() {
	return 10;
    }

    public int getLogicDelay() {
	return 30;
    }

    public void onMousePressed(MouseEvent evt) {
        String button = ((JButton)evt.getSource()).getText();
        if (button.equals("Login")) {
            String userName = LoginScreen.userField.getText();
            String passWord = LoginScreen.passField.getText();
        } else if (button.equals("Register")) {

        } else if (button.equals("Website")) {

        }
    }

    public void onMouseReleased(MouseEvent evt) {

    }

    public void onMouseMoved(MouseEvent evt) {

    }

    public void onMouseDragged(MouseEvent evt) {

    }

    public void onKeyPressed(KeyEvent evt) {

    }

    public void onKeyReleased(KeyEvent evt) {
		
    }

    public abstract void doLogics();

    public abstract void doRender(Graphics2D g);
}
