/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.canvas;

import java.awt.Graphics2D;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author David
 */
public class LoginScreen extends AbstractCanvas {
    static JTextField userField = new JTextField("");
    static JPasswordField passField = new JPasswordField("");
    JButton login = new JButton("Login");
    JButton register = new JButton("Register");
    JButton website = new JButton("Website");

    @Override
    public void doLogics() {

    }

    @Override
    public void doRender(Graphics2D g) {
		
    }
}
