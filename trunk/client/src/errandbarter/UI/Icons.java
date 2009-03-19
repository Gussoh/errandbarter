/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package errandbarter.UI;

import java.io.IOException;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Gussoh
 */
public class Icons {

    public Image user;

    private static Icons instance;

    private Icons() {
        try {
            user = Image.createImage("/user.png");
        } catch (IOException ex) {
            ex.printStackTrace();
            user = Image.createImage(0, 0);
        }
        
    }

    public static Icons getInstance() {
        if (instance == null) {
            instance = new Icons();
        }

        return instance;
    }
}
