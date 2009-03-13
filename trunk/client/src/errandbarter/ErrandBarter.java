/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package errandbarter;

import errandbarter.UI.MainForm;
import errandbarter.UI.WelcomeForm;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author Gussoh
 */
public class ErrandBarter extends MIDlet {

    ServerConnection serverConnection;

    protected void startApp() throws MIDletStateChangeException {
        try {
            serverConnection = new ServerConnection(this, RecordManager.getServerAddress(), RecordManager.getUserId());
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
            serverConnection = new ServerConnection(this, null, null);
        }
        WelcomeForm wf = new WelcomeForm(this);
        if (serverConnection.getAddress() == null || serverConnection.getUserId() == null) {
            Display.getDisplay(this).setCurrent(wf);
        } else {
            MainForm mf = new MainForm(this);
            serverConnection.getUserInfo(mf, mf, wf);
        }
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    

}
