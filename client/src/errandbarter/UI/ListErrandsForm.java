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
public class ListErrandsForm extends Form implements DataListener, CommandListener, ItemCommandListener {

    private ErrandBarter eb;
    private Command backCommand = new Command("Back", Command.BACK, 0);
    private Command openCommand = new Command("Open", Command.ITEM, 0);
    private Displayable previous;
    private Item[] errandsItems;
    private int[] errandsIds;

    public ListErrandsForm(ErrandBarter eb, Displayable previous) {
        super("Errands List");
        this.eb = eb;
        this.eb = eb;
        this.previous = previous;
        addCommand(backCommand);
        setCommandListener(this);
    }

    public void onErrandsList(Vector errands, String command, String[] arguments) {
        errandsItems = new StringItem[errands.size()];
        errandsIds = new int[errands.size()];

        for (int i = 0; i < errands.size(); i++) {
            Errand errand = (Errand) errands.elementAt(i);
            errandsItems[i] = new StringItem("$" + errand.getPrice(), errand.getDescription().replace('\n', ' ').trim());
            errandsItems[i].setDefaultCommand(openCommand);
            errandsItems[i].setItemCommandListener(this);
            errandsIds[i] = errand.getId();
            append(errandsItems[i]);
        }
    }

    public void onViewErrand(Errand errand, String command, String[] arguments) {
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
        if (c == openCommand) {
            for (int i = 0; i < errandsItems.length; i++) {
                if (item == errandsItems[i]) {
                    errandbarter.UI.ErrandForm vef = new ErrandForm(eb, this);
                    eb.getServerConnection().getErrand(errandsIds[i], vef, vef, this);
                }
            }
        }
    }
}
