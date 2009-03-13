/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package errandbarter.UI;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Item;

/**
 *
 * @author Gussoh
 */
public class TransferDisplay extends Form {

    private Display display;
    private Gauge progress = new Gauge(null, false, Gauge.INDEFINITE, Gauge.CONTINUOUS_RUNNING);

    public TransferDisplay(Display display) {
        super("Talking to server...");
        progress.setLayout(Item.LAYOUT_CENTER | Item.LAYOUT_VCENTER | Item.LAYOUT_VEXPAND);
        this.display = display;
        append(progress);
        //new Timer().start();
    }
}
