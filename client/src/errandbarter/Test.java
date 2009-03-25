/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package errandbarter;

import errandbarter.UI.IconItem;
import errandbarter.UI.Icons;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 *
 * @author Gussoh
 */
public class Test extends MIDlet {

    protected void startApp() throws MIDletStateChangeException {
        Form f = new Form("Test");
        IconItem i = new IconItem(this, Icons.getInstance().user, "Test", "Hello boy!");
        f.append(new IconItem(this, Icons.getInstance().user, "Hello", "Are you a horse?"));
        f.append(i);
        i.setDefaultCommand(new Command("bry", Command.ITEM, 0));
        Display.getDisplay(this).setCurrent(f);
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }

}
