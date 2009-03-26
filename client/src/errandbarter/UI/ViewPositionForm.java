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
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.location.QualifiedCoordinates;

/**
 *
 * @author Gussoh
 */
public class ViewPositionForm extends Form implements CommandListener, ItemCommandListener {

    private ErrandBarter eb;
    private Displayable previous;
    private Command backCommand = new Command("Back", Command.BACK, 0);
    private Command openCommand = new Command("Open", Command.ITEM, 0);
    private IconItem positionItem;
    private IconItem updateItem;

    public ViewPositionForm(ErrandBarter eb, Displayable previous) {
        super("Position");
        this.eb = eb;
        this.previous = previous;

        addCommand(backCommand);
        setCommandListener(this);

        QualifiedCoordinates pos = eb.getPositioning().getPosition();

        positionItem = new IconItem(eb, Icons.getInstance().position, "Position", "Lat: " + pos.getLatitude() + ", long: " + pos.getLongitude());
        append(positionItem);

        updateItem = new IconItem(eb, Icons.getInstance().update, "Update", "");
        updateItem.setDefaultCommand(openCommand);
        updateItem.setItemCommandListener(this);
        append(updateItem);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == backCommand) {
            Display.getDisplay(eb).setCurrent(previous);
        }
    }

    public void commandAction(Command c, Item item) {
        QualifiedCoordinates pos = eb.getPositioning().getPosition();

        positionItem.setText("Lat: " + pos.getLatitude() + ", long: " + pos.getLongitude());
    }
}
