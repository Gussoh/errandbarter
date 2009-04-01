/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package errandbarter.UI;

import errandbarter.ServerConnection.FetcherCanceller;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Item;

/**
 *
 * @author Gussoh
 */
public class TransferDisplay extends Form implements CommandListener {

    private Display display;
    private Gauge progress = new Gauge(null, false, Gauge.INDEFINITE, Gauge.CONTINUOUS_RUNNING);
    private FetcherCanceller fc;
    private Command cancelCommand = new Command("Cancel", Command.CANCEL, 0);

    public TransferDisplay(Display display, FetcherCanceller fc) {
        super("Talking to server...");
        progress.setLayout(Item.LAYOUT_CENTER | Item.LAYOUT_VCENTER | Item.LAYOUT_VEXPAND);
        this.fc = fc;
        this.display = display;

        addCommand(cancelCommand);
        setCommandListener(this);
        
        append(progress);
        //new Timer().start();
    }

    public void commandAction(Command c, Displayable d) {
        removeCommand(cancelCommand);
        System.out.println("CANCELLING!!!");
        fc.cancel();
    }
}
