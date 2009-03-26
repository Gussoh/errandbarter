/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package errandbarter;

import errandbarter.UI.AquirePositionForm;
import errandbarter.UI.MainForm;
import errandbarter.UI.WelcomeForm;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.location.LocationException;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author Gussoh
 */
public class ErrandBarter extends MIDlet {

    private ServerConnection serverConnection;
    private Positioning positioning;
    public final int version = 1;

    protected void startApp() throws MIDletStateChangeException {
        WelcomeForm wf = new WelcomeForm(this);
        try {
            if (positioning == null) {
                System.out.println("CREATING POSITIONING");
                positioning = new Positioning();
                System.out.println("POSITIONING CREATED");
            }
            positioning.start();
        } catch (LocationException ex) {
            ex.printStackTrace();
            Display.getDisplay(this).setCurrent(new Alert("Positioning failed: " + ex.getMessage()), wf);
        }

        try {
            serverConnection = new ServerConnection(this, RecordManager.getServerAddress(), RecordManager.getUserId());
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
            serverConnection = new ServerConnection(this, null, null);
        }

        if (serverConnection.getAddress() == null || serverConnection.getUserId() == null) {
            Display.getDisplay(this).setCurrent(wf);
        } else {
            AquirePositionForm apf = new AquirePositionForm(this);
            Display.getDisplay(this).setCurrent(apf);
        }

    }

    protected void pauseApp() {
        if (positioning != null) {
            positioning.stop();
        }
    }

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
        if (positioning != null) {
            positioning.stop();
        }
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    public Positioning getPositioning() {
        return positioning;
    }
}
