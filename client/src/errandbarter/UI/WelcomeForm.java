/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package errandbarter.UI;

import errandbarter.DataListener;
import errandbarter.Errand;
import errandbarter.ErrandBarter;
import errandbarter.OperationException;
import errandbarter.RecordManager;
import errandbarter.User;
import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author Gussoh
 */
public class WelcomeForm extends Form implements CommandListener, DataListener {

    private ErrandBarter eb;
    private TextField serverAddress = new TextField("Server Address", "http://155.69.104.36:8084/errandbarterserver/", 64, TextField.URL);
    private TextField userId = new TextField("Username", "", 64, TextField.ANY);
    private StringItem errorText = new StringItem("", "");
    private MainForm mf;

    public WelcomeForm(ErrandBarter eb) {
        super("Welcome");
        this.eb = eb;

        append(new StringItem("Setup", "Please enter some vital information:"));
        append(serverAddress);
        append(userId);
        append(errorText);
        addCommand(new Command("Contine", Command.OK, 0));
        setCommandListener(this);
    }

    public void commandAction(Command c, Displayable d) {
        mf = new MainForm(eb);
        eb.getServerConnection().setAddress(serverAddress.getString());
        eb.getServerConnection().setUserId(userId.getString());
        eb.getServerConnection().getUserInfo(this, mf, null); // manual error handeling
    }

    public void onError(Exception e) {
        if (e instanceof OperationException) {
            serverAddress.setLabel("Server Address");
            userId.setLabel("Username");
            errorText.setText("Server response unexpected, correct username? (" + e.getMessage() + ")");
        } else {
            serverAddress.setLabel("Server Address [error]");
            errorText.setText("Communication error with server. Correct URL? (" + e.getMessage() + ")");
        }

        Display.getDisplay(eb).setCurrent(this);
    }

    public void onErrandsList(Vector errands, String command, String[] arguments) {
    }

    public void onViewErrand(Errand errand, String command, String[] arguments) {
    }

    public void onUserInfo(User user, String command, String[] arguments) {
        try {
            RecordManager.setServerAddressAndUserId(serverAddress.getString(), userId.getString());
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }

        mf.onUserInfo(user, command, arguments);
    }

    public void onOK(String command, String[] arguments) {
    }
}
