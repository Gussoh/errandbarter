/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package errandbarter.UI;

import errandbarter.ErrandBarter;
import errandbarter.RecordManager;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author Gussoh
 */
public class OptionsForm extends Form implements CommandListener, ItemCommandListener {

    private ErrandBarter eb;
    private Displayable previous;
    private Command openCommand = new Command("Open", Command.ITEM, 0);
    private Command backCommand = new Command("Back", Command.BACK, 0);
    private IconItem serverUserNameItem;
    private IconItem aboutItem;
    private IconItem locationItem;
    private IconItem deleteAllStoredData;

    public OptionsForm(ErrandBarter eb, Displayable previous) {
        super("Options");
        this.eb = eb;
        this.previous = previous;

        serverUserNameItem = new IconItem(eb, Icons.getInstance().server, "Server & Username", "");
        serverUserNameItem.setItemCommandListener(this);
        serverUserNameItem.setDefaultCommand(openCommand);
        append(serverUserNameItem);

        locationItem = new IconItem(eb, Icons.getInstance().location, "Current location", "");
        locationItem.setItemCommandListener(this);
        locationItem.setDefaultCommand(openCommand);
        append(locationItem);

        deleteAllStoredData = new IconItem(eb, Icons.getInstance().delete, "Delete stored data on phone", "");
        deleteAllStoredData.setDefaultCommand(openCommand);
        deleteAllStoredData.setItemCommandListener(this);
        append(deleteAllStoredData);

        aboutItem = new IconItem(eb, Icons.getInstance().about, "About", "");
        aboutItem.setItemCommandListener(this);
        aboutItem.setDefaultCommand(openCommand);
        append(aboutItem);

        addCommand(backCommand);
        setCommandListener(this);

    }

    public void commandAction(Command c, Displayable d) {
        if (c == backCommand) {
            Display.getDisplay(eb).setCurrent(previous);
        }
    }

    public void commandAction(Command c, Item item) {
        if (item == serverUserNameItem) {
            WelcomeForm wf = new WelcomeForm(eb);
            Display.getDisplay(eb).setCurrent(wf);
        } else if (item == locationItem) {
            ViewPositionForm vpf = new ViewPositionForm(eb, this);
            Display.getDisplay(eb).setCurrent(vpf);
        } else if (item == aboutItem) {
            AboutForm af = new AboutForm(eb, this);
            Display.getDisplay(eb).setCurrent(af);
        } else if (item == deleteAllStoredData) {

            Alert a = new Alert("All data will be removed and appliation terminated!");
            a.setType(AlertType.WARNING);
            a.setTimeout(6000);
            a.setImage(Icons.getInstance().delete);
            Display.getDisplay(eb).setCurrent(a, new Form("..."));
            Runnable r = new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(5000);
                        RecordManager.clearData();
                        eb.notifyDestroyed();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } catch (RecordStoreException ex) {
                        Display.getDisplay(eb).setCurrent(new Alert("Error deleting: " + ex.getMessage()), OptionsForm.this);
                        ex.printStackTrace();
                    }
                }
            };
            new Thread(r).start();
        }
    }
}
