/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package errandbarter.UI;

import errandbarter.ErrandBarter;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;

/**
 *
 * @author Gussoh
 */
public class AquirePositionForm extends Form implements CommandListener {

    ErrandBarter eb;
    Timer t = new Timer();
    private int timeout = 60;
    StringItem timeoutItem = new StringItem("Timeout", "");

    public AquirePositionForm(final ErrandBarter eb) {
        super("Getting position...");

        this.eb = eb;

        append("Getting position...");
        Gauge g = new Gauge(null, false, Gauge.INDEFINITE, Gauge.CONTINUOUS_RUNNING);
        g.setLayout(Item.LAYOUT_CENTER | Item.LAYOUT_VCENTER | Item.LAYOUT_VEXPAND);
        append(g);
        append(timeoutItem);

        addCommand(new Command("Cancel", Command.STOP, 0));
        setCommandListener(this);

        TimerTask tt = new TimerTask() {

            public void run() {
                System.out.println("POSITIONING: " + eb.getPositioning().getPosition());
                if (eb.getPositioning().getPosition() != null || timeout <= 0) {
                    gotoNext();
                }
                timeoutItem.setText(timeout + "");
                timeout--;
            }
        };


        t.scheduleAtFixedRate(tt, 0, 1000);
    }

    private void gotoNext() {
        System.out.println("GOTO NEXT!");
        t.cancel();
        Display.getDisplay(eb).setCurrent(new MainForm(eb));
        try {
            Thread.sleep(50); // some threading issue...
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Display.getDisplay(eb).setCurrent(new MainForm(eb));
    }

    public void commandAction(Command c, Displayable d) {
        gotoNext();
    }
}
