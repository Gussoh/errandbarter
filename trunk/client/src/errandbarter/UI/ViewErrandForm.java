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
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.StringItem;

/**
 *
 * @author Gussoh
 */
public class ViewErrandForm extends Form implements DataListener, CommandListener, ItemCommandListener {

    private ErrandBarter eb;
    private Command backCommand = new Command("Back", Command.BACK, 0);

    private StringItem ownerItem = new StringItem("Owner", "?");
    private StringItem descriptionItem = new StringItem("Description", "?");

    private Displayable previous;

    public ViewErrandForm(ErrandBarter eb, Displayable previous) {
        super("View Errand");
        this.eb = eb;
        this.previous = previous;
        addCommand(backCommand);
        setCommandListener(this);

        append(ownerItem);
        append(descriptionItem);
    }

    public void onErrandsList(Vector errands, String command, String[] arguments) {
    }

    public void onViewErrand(Errand errand, String command, String[] arguments) {
        setTitle("Errand  $" + errand.getPrice());
        ownerItem.setText(errand.getUser());
        descriptionItem.setText(errand.getDescription());
    }

    public void onUserInfo(User user, String command, String[] arguments) {
    }

    public void onOK(String command, String[] arguments) {
    }

    public void onError(Exception e) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == backCommand) {
            Display.getDisplay(eb).setCurrent(previous);
        }
    }

    public void commandAction(Command c, Item item) {
        
    }
}
