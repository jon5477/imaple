/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.canvas;

import imaple.Core;
import imaple.GameObject;
import imaple.ex.CoreyIsAFaggotException;
import imaple.ex.ICantCompileException;
import imaple.network.PacketCreator;
import imaple.ui.UiActionEvent;
import imaple.ui.UiActionListener;
import imaple.ui.UiButton;
import imaple.ui.UiTextbox;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class LoginScreen extends AbstractCanvas {
    //Seth: I challenge you: Get this part to work. (Hint: use the included event handlers and renderers)
    // (By Seth) Sure, I like a good challenge ^^.
    private static UiTextbox usernameBox, passwordBox; // argh blasphemy..
    private UiButton loginButton; // argh blasphemy..
    private Core c; // meh..

    public LoginScreen() {
        Image loginButtonImages[] = new Image[4];
        loginButton = new UiButton(loginButtonImages, 84, 40, UiButton.getUiManager());
        loginButton.setWidth(638);
        loginButton.setHeight(258);
        loginButton.addActionListener(new UiActionListener() {
            @Override public void actionPerformed(UiActionEvent evt) {
                String username = getUsername();
                String password = getPassword();
                if (username == null || password == null)
                    return; // Gotta start working on client error messages =/
                try {
                    c.getSession().sendPacket(PacketCreator.sendLogin(username, password));
                } catch (CoreyIsAFaggotException ex) {
                    Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ICantCompileException ex) {
                    Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public static String getUsername() {
        return usernameBox.getText();
    }

    public static String getPassword() {
        return passwordBox.getText();
    }

    @Override
    public void doLogics() {

    }

    @Override
    public void doRender(Graphics2D g) {
        g.setColor(Color.WHITE);
        loginButton.render(g);
    }
}
