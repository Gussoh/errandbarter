/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package errandbarter.UI;

import errandbarter.ErrandBarter;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

/**
 *
 * @author Gussoh
 */
public class FatalErrorForm extends Form implements CommandListener {

    private ErrandBarter eb;

    public FatalErrorForm(ErrandBarter eb) {
        super("Fatal error");
        this.eb = eb;
        append("There was an fatal error. Try and check your Internet connection and restarting the application.");
        addCommand(new Command("Quit", Command.EXIT, 0));
        setCommandListener(this);
    }

    public void commandAction(Command c, Displayable d) {
        eb.notifyDestroyed();
    }


}
