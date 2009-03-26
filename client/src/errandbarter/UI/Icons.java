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

    public Image user, update, errand, errand_own, errand_list, errand_more, errand_answered, balance, 
            balance_disposable, description, answer_add, answer, location, position, distance, clock,
            reward, options, server, about, delete, phone;

    private static Icons instance;

    private Icons() {
        try {
            user = Image.createImage("/user.png");
            update = Image.createImage("/arrow_rotate_clockwise.png");
            errand = Image.createImage("/script.png");
            errand_own = Image.createImage("/script_edit.png");
            errand_more = Image.createImage("/script_add.png");
            errand_answered = Image.createImage("/script_lightning.png");
            errand_list = Image.createImage("/script_go.png");
            balance = Image.createImage("/money_add.png");
            balance_disposable = Image.createImage("/money.png");
            description = Image.createImage("/text_dropcaps.png");
            answer = Image.createImage("/lightning.png");
            answer_add = Image.createImage("/lightning_add.png");
            location = Image.createImage("/house.png");
            position = Image.createImage("/map.png");
            distance = Image.createImage("/car.png");
            clock = Image.createImage("/clock.png");
            reward = Image.createImage("/money_add.png");
            options = Image.createImage("/wrench.png");
            server = Image.createImage("/server.png");
            about = Image.createImage("/information.png");
            delete = Image.createImage("/cross.png");
            phone = Image.createImage("/phone.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    public static Icons getInstance() {
        if (instance == null) {
            instance = new Icons();
        }

        return instance;
    }
}
