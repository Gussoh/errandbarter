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
public class UserInfoForm extends Form implements DataListener, CommandListener, ItemCommandListener {

    private StringItem username = new StringItem("Username", "?");
    private StringItem balance = new StringItem("Balance", "?");
    private StringItem disposable = new StringItem("Disposable Balance", "?");

    private StringItem listAnsweredErrands = new StringItem("List errands", "Answered by user");
    private StringItem listCreatedErrands = new StringItem("List errands", "Created by user");

    private ErrandBarter eb;
    private Displayable previous;

    private User user;

    private Command backCommand = new Command("Back", Command.BACK, 1);
    private Command openCommand = new Command("Open", Command.ITEM, 0);

    public UserInfoForm(ErrandBarter eb, Displayable previous) {
        super("User info");
        this.previous = previous;
        this.eb = eb;
        append(username);
        append(balance);
        append(disposable);

        listAnsweredErrands.setDefaultCommand(openCommand);
        listAnsweredErrands.setItemCommandListener(this);
        listCreatedErrands.setDefaultCommand(openCommand);
        listCreatedErrands.setItemCommandListener(this);

        append(listAnsweredErrands);
        append(listCreatedErrands);
        addCommand(backCommand);
        setCommandListener(this);
    }


    public void commandAction(Command c, Displayable d) {
        if (c == backCommand) {
            Display.getDisplay(eb).setCurrent(previous);
        }
    }

    public void onErrandsList(Vector errands, String command, String[] arguments) {
    }

    public void onViewErrand(Errand errand, String command, String[] arguments) {
    }

    public void onUserInfo(User user, String command, String[] arguments) {
        this.user = user;
        username.setText(user.getId());
        balance.setText(user.getBalance() + "");
        disposable.setText(user.getBalance() + "");
    }

    public void onOK(String command, String[] arguments) {
    }

    public void onError(Exception e) {
    }

    public void commandAction(Command c, Item item) {
        if (item == listAnsweredErrands) {
            ListErrandsForm lef = new ListErrandsForm(eb, this);
            eb.getServerConnection().listErrandsPerformedBy(user.getId(), lef, lef, this);
        } else if (item == listCreatedErrands) {
            ListErrandsForm lef = new ListErrandsForm(eb, this);
            eb.getServerConnection().listErrandsBy(user.getId(), lef, lef, this);
        }
    }
}
