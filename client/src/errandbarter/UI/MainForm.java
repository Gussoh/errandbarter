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

/**
 *
 * @author Gussoh
 */
public class MainForm extends Form implements DataListener, CommandListener, ItemCommandListener {

    public final int SPACER_SIZE = 15;
    private ErrandBarter eb;
    private IconItem userInfo;
    private IconItem[] locationErrands = new IconItem[3];
    private IconItem listNearErrands;
    private IconItem ownErrands;
    private int[] locationErrandsIds = new int[locationErrands.length];
    private Command[] locationErrandCommands = new Command[locationErrands.length];
    private IconItem updateItem;
    private IconItem optionsItem;

    private final Command openItemCommand = new Command("Open", Command.ITEM, 0);

    public MainForm(ErrandBarter eb) {
        super("Errand Barter");
        this.eb = eb;

        userInfo = new IconItem(eb, Icons.getInstance().user, "User: ?", "$?");
        updateItem = new IconItem(eb, Icons.getInstance().update, "Update", "");
        listNearErrands = new IconItem(eb, Icons.getInstance().errand_list, "List more errands near you", "");
        ownErrands = new IconItem(eb, Icons.getInstance().errand_own, "Own errands", "?");
        optionsItem = new IconItem(eb, Icons.getInstance().options, "Options", "");

        append(userInfo);
        userInfo.setItemCommandListener(this);
        setCommandListener(this);
        userInfo.setDefaultCommand(new Command("More info", Command.ITEM, 1));

        append(new SpacerTwo(SPACER_SIZE, SPACER_SIZE));
        
        ownErrands.setDefaultCommand(new Command("List own errands", Command.ITEM, 0));
        ownErrands.setItemCommandListener(this);
        append(ownErrands);

        append(new SpacerTwo(SPACER_SIZE, SPACER_SIZE));

        append(new IconItem(eb, Icons.getInstance().errand, "Errands close to you", ""));
        for (int i = 0; i < locationErrands.length; i++) {
            locationErrands[i] = new IconItem(eb, Icons.getInstance().errand, "", "");
            append(locationErrands[i]);
            locationErrands[i].setItemCommandListener(this);
            locationErrandCommands[i] = openItemCommand;
        }

        listNearErrands.setDefaultCommand(openItemCommand);
        listNearErrands.setItemCommandListener(this);
        append(listNearErrands);
        
        append(new SpacerTwo(SPACER_SIZE, SPACER_SIZE));
        
        updateItem.setDefaultCommand(openItemCommand);
        updateItem.setItemCommandListener(this);
        append(updateItem);

        optionsItem.setDefaultCommand(openItemCommand);
        optionsItem.setItemCommandListener(this);
        append(optionsItem);

        new Thread(new Runnable() {

            public void run() {
                try {
                    Thread.sleep(100);
                    update();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public void update() {
        eb.getServerConnection().getUserInfo(this, this, this);
        eb.getServerConnection().listOwnErrands(this, this, this);
        eb.getServerConnection().listErrands(this, this, this);
    }

    public void commandAction(Command c, Displayable d) {
    }

    public void commandAction(Command c, Item item) {
        if (item == optionsItem) {
            OptionsForm of = new OptionsForm(eb, this);
            Display.getDisplay(eb).setCurrent(of);
        } else if (item == userInfo) {
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

            ownErrands.setText(errands.size() + " errands with " + ownErrandsAnswerCount + " answers");
        } else {
            for (int i = 0; i < locationErrands.length; i++) {
                IconItem locationErrand = locationErrands[i];
                locationErrand.setDefaultCommand(null);
                locationErrand.setText("");
                locationErrand.setLabel("-");
            }
            for (int i = 0; i < errands.size() && i < locationErrands.length; i++) {
                Errand errand = (Errand) errands.elementAt(i);
                locationErrands[i].setLabel("$" + errand.getPrice());
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
        userInfo.setText("$" + user.getDisposableBalance());
    }

    public void onOK(String command, String[] arguments) {
    }

    public void onError(Exception e) {
    }
}
