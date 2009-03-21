/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package errandbarter.UI;

import errandbarter.DataListener;
import errandbarter.Errand;
import errandbarter.ErrandBarter;
import errandbarter.User;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author Gussoh
 */
public class WriteAnswerForm extends TextBox implements DataListener, CommandListener {

    private ErrandBarter eb;
    private Displayable previous;
    private Errand errand;

    private Command backCommand = new Command("Back", Command.BACK, 0);
    private Command submitCommand = new Command("Submit", Command.SCREEN, 0);

    public WriteAnswerForm(ErrandBarter eb, Displayable previous, Errand errand) {
        super("Answer", "", 4096, TextField.ANY);
        this.eb = eb;
        this.previous = previous;
        this.errand = errand;

        addCommand(backCommand);
        addCommand(submitCommand);
        setCommandListener(this);

    }

    public void onErrandsList(Vector errands, String command, String[] arguments) {
    }

    public void onViewErrand(Errand errand, String command, String[] arguments) {
    }

    public void onUserInfo(User user, String command, String[] arguments) {
    }

    public void onOK(String command, String[] arguments) {
        Display.getDisplay(eb).setCurrent(new Alert("Submit successful!"), previous);
    }

    public void onError(Exception e) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == backCommand) {
            Display.getDisplay(eb).setCurrent(previous);
        } else if (c == submitCommand) {
            if (size() < 1) {
                Display.getDisplay(eb).setCurrent(new Alert("You must enter an answer."), this);
            } else {
                eb.getServerConnection().performErrand(errand.getId(), getString(), this, null, this);
            }
        }
    }

}
