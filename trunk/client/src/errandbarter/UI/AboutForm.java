/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package errandbarter.UI;

import errandbarter.ErrandBarter;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

/**
 *
 * @author Gussoh
 */
public class AboutForm extends Form implements CommandListener {

    private ErrandBarter eb;
    private Displayable previous;

    private Command backCommand = new Command("Back", Command.BACK, 0);

    private IconItem versionItem;

    public AboutForm(ErrandBarter eb, Displayable previous) {
        super("About");
        this.eb = eb;
        this.previous = previous;

        addCommand(backCommand);
        setCommandListener(this);

        versionItem = new IconItem(eb, Icons.getInstance().errand, "Version", eb.version + "");
        append(versionItem);

        append("This application is developed at Nanyang Technological University 2009");
        
        append(new IconItem(eb, Icons.getInstance().server, "Server developer", "Daniel Tan"));
        append(new IconItem(eb, Icons.getInstance().server, "Server developer", "Zach Lim"));
        append(new IconItem(eb, Icons.getInstance().phone, "Client developer", "Gustav Sohtell"));


    }

    public void commandAction(Command c, Displayable d) {
        if (c == backCommand) {
            Display.getDisplay(eb).setCurrent(previous);
        }
    }

}
