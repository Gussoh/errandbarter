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
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.Spacer;
import javax.microedition.lcdui.StringItem;

/**
 *
 * @author Gussoh
 */
public class MainForm extends Form implements DataListener, CommandListener, ItemCommandListener {

    public final int SPACER_SIZE = 30;
    private ErrandBarter eb;
    private StringItem userInfo = new StringItem("User: ?", "$: ?");
    private StringItem[] locationErrands = new StringItem[3];
    private StringItem listNearErrands = new StringItem("List errand", "List errands near you");
    private StringItem ownErrands = new StringItem("Own errands", "?");
    private int[] locationErrandsIds = new int[locationErrands.length];
    private Command[] locationErrandCommands = new Command[locationErrands.length];
    private StringItem updateItem = new StringItem("Update", "Update info");

    private final Command openItemCommand = new Command("Open", Command.ITEM, 0);

    public MainForm(ErrandBarter eb) {
        super("Errand Barter");
        this.eb = eb;

        append(userInfo);
        userInfo.setItemCommandListener(this);
        setCommandListener(this);
        userInfo.setDefaultCommand(new Command("More info", Command.ITEM, 1));

        append(new Spacer(SPACER_SIZE, SPACER_SIZE));
        ownErrands.setDefaultCommand(new Command("List own errands", Command.ITEM, 0));
        ownErrands.setItemCommandListener(this);
        append(ownErrands);

        append(new Spacer(SPACER_SIZE, SPACER_SIZE));

        append("Errands close to you:");
        for (int i = 0; i < locationErrands.length; i++) {
            locationErrands[i] = new StringItem("", "");
            append(locationErrands[i]);
            locationErrands[i].setItemCommandListener(this);
            locationErrandCommands[i] = openItemCommand;
        }

        listNearErrands.setDefaultCommand(openItemCommand);
        listNearErrands.setItemCommandListener(this);
        append(listNearErrands);

        append(new Spacer(SPACER_SIZE, SPACER_SIZE));

        updateItem.setDefaultCommand(openItemCommand);
        updateItem.setItemCommandListener(this);
        append(updateItem);

        update();
    }

    public void update() {
        eb.getServerConnection().getUserInfo(this, this, this);
        eb.getServerConnection().listOwnErrands(this, this, this);
        eb.getServerConnection().listErrands(this, this, this);
    }

    public void commandAction(Command c, Displayable d) {
    }

    public void commandAction(Command c, Item item) {
        if (item == userInfo) {
            UserInfoForm uif = new UserInfoForm(eb, this);
            eb.getServerConnection().getUserInfo(uif, uif, this);
        } else if(item == ownErrands) {
            ListErrandsForm lef = new ListErrandsForm(eb, this);
            eb.getServerConnection().listOwnErrands(lef, lef, this);
        } else if (item == updateItem) {
            update();
        } else if (item == listNearErrands) {
            ListErrandsForm lef = new ListErrandsForm(eb, this);
            eb.getServerConnection().listErrands(lef, lef, this);
        } else if (c == openItemCommand) {
            for (int i = 0; i < locationErrands.length; i++) {
                if (item == locationErrands[i]) {
                    ErrandForm vef = new ErrandForm(eb, this);
                    eb.getServerConnection().getErrand(locationErrandsIds[i], vef, vef, this);
                }
            }
        }
    }

    public void onErrandsList(Vector errands, String command, String[] arguments) {
        boolean listingOwnErrands = false;
        for (int i = 0; i < arguments.length; i++) {
            String argument = arguments[i];
            if (argument != null && argument.toLowerCase().startsWith("user=") && argument.toLowerCase().endsWith("=" + eb.getServerConnection().getUserId())) {
                listingOwnErrands = true;
            }
        }

        if (listingOwnErrands) {
            int ownErrandsCount = 0;
            int ownErrandsAnswerCount = 0;
            for (int i = 0; i < errands.size(); i++) {
                Errand errand = (Errand) errands.elementAt(i);
                ownErrandsCount++;
                ownErrandsAnswerCount += errand.getAnswers().size();
            }

            ownErrands.setText(errands.size() + " errands with " + ownErrandsAnswerCount + " answers.");
        } else {
            for (int i = 0; i < locationErrands.length; i++) {
                StringItem locationErrand = locationErrands[i];
                locationErrand.setDefaultCommand(null);
                locationErrand.setText("");
                locationErrand.setLabel("No errand");
            }
            for (int i = 0; i < errands.size() && i < locationErrands.length; i++) {
                Errand errand = (Errand) errands.elementAt(i);
                locationErrands[i].setLabel("Errand, $" + errand.getPrice());
                locationErrands[i].setText(errand.getDescription().replace('\n', ' ').trim());
                locationErrands[i].setDefaultCommand(openItemCommand);
                locationErrandsIds[i] = errand.getId();
            }
        }
    }

    public void onViewErrand(Errand errand, String command, String[] arguments) {
    }

    public void onUserInfo(User user, String command, String[] arguments) {
        userInfo.setLabel("You: " + user.getId());
        userInfo.setText("$: " + user.getDisposableBalance());
    }

    public void onOK(String command, String[] arguments) {
    }

    public void onError(Exception e) {
    }
}
