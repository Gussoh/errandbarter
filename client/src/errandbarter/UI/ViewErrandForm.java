/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package errandbarter.UI;

import errandbarter.Answer;
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
import javax.microedition.lcdui.StringItem;

/**
 *
 * @author Gussoh
 */
public class ViewErrandForm extends Form implements DataListener, CommandListener, ItemCommandListener {

    private ErrandBarter eb;
    private Command backCommand = new Command("Back", Command.BACK, 0);
    private Command openCommand = new Command("Open", Command.ITEM, 0);

    private StringItem ownerItem = new StringItem("Owner", "?");
    private StringItem descriptionItem = new StringItem("Description", "?");
    private StringItem updateItem = new StringItem("Update", "Fetch update");

    private Errand errand;

    private StringItem[] answerItems;
    private Answer[] answers;

    private Displayable previous;

    public ViewErrandForm(ErrandBarter eb, Displayable previous) {
        super("View Errand");
        this.eb = eb;
        this.previous = previous;
        addCommand(backCommand);
        setCommandListener(this);

        ownerItem.setDefaultCommand(openCommand);
        ownerItem.setItemCommandListener(this);
        append(ownerItem);
        append(descriptionItem);

    }

    public void onErrandsList(Vector errands, String command, String[] arguments) {
    }

    public void onViewErrand(Errand errand, String command, String[] arguments) {
        this.errand = errand;
        setTitle("Errand  $" + errand.getPrice());
        ownerItem.setText(errand.getUser());
        descriptionItem.setText(errand.getDescription().trim());

        answerItems = new StringItem[errand.getAnswers().size()];
        answers = new Answer[errand.getAnswers().size()];

        for (int i = 0; i < errand.getAnswers().size(); i++) {
            Answer answer = (Answer) errand.getAnswers().elementAt(i);
            answerItems[i] = new StringItem("Answer", answer.getAnswer().replace('\n', ' ').trim());
            answers[i] = answer;
            answerItems[i].setDefaultCommand(openCommand);
            answerItems[i].setItemCommandListener(this);
            append(answerItems[i]);
        }


        updateItem.setDefaultCommand(openCommand);
        updateItem.setItemCommandListener(this);
        append(updateItem);
    }

    public void onUserInfo(User user, String command, String[] arguments) {
    }

    public void onOK(String command, String[] arguments) {
    }

    public void onError(Exception e) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == backCommand) {
            Display.getDisplay(eb).setCurrent(previous);
        }
    }

    public void commandAction(Command c, Item item) {
        if (item == ownerItem) {
            UserInfoForm uif = new UserInfoForm(eb, this);
            eb.getServerConnection().getUserInfo(errand.getUser(), uif, uif, this);
        } else if (item == updateItem) {
            ViewErrandForm vef = new ViewErrandForm(eb, previous);
            eb.getServerConnection().getErrand(errand.getId(), vef, vef, this);
        } else if (c == openCommand) {
            for (int i = 0; i < answerItems.length; i++) {
                if(item == answerItems[i]) {
                    AnswerForm af = new AnswerForm(eb, this, answers[i], errand.isRewarded());
                    Display.getDisplay(eb).setCurrent(af);
                }
            }
        }
    }
}
