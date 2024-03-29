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
import java.util.Date;
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
public class AnswerForm extends Form implements CommandListener, ItemCommandListener, DataListener {

    private ErrandBarter eb;
    private Command backCommand = new Command("Back", Command.BACK, 0);
    private Command openCommand = new Command("Open", Command.ITEM, 0);
    private Command rewardCommand = new Command("Give reward", Command.ITEM, 0);
    private final Answer answer;
    private final Displayable previous;
    private IconItem userItem;
    private StringItem answerItem;
    private IconItem locationItem;
    private IconItem timeItem;
    private IconItem rewardedItem;
    private final boolean questionRewarded;
    private Errand errand;

    public AnswerForm(ErrandBarter eb, Displayable previous, Answer answer, Errand errand) {
        super("Answer");
        this.eb = eb;
        this.answer = answer;
        this.previous = previous;
        this.questionRewarded = errand.isRewarded();
        this.errand = errand;

        addCommand(backCommand);
        setCommandListener(this);

        userItem = new IconItem(eb, Icons.getInstance().user, "User", answer.getUser());
        userItem.setDefaultCommand(openCommand);
        userItem.setItemCommandListener(this);
        append(userItem);

        answerItem = new StringItem("Answer", answer.getAnswer().trim());
        append(answerItem);

        locationItem = new IconItem(eb, Icons.getInstance().location, "Location", "lat: " + answer.getLocation().getLatitude() + " long: " + answer.getLocation().getLongitude());
        append(locationItem);

        timeItem = new IconItem(eb, Icons.getInstance().clock, "Time", new Date(answer.getTimestamp()).toString());
        append(timeItem);

        if (answer.getPointsRewarded() > 0) {
            rewardedItem = new IconItem(eb, Icons.getInstance().reward, "Rewarded", "Yes");
        } else {
            rewardedItem = new IconItem(eb, Icons.getInstance().reward, "Rewarded", "No");
            if (!questionRewarded && errand.getUser().equalsIgnoreCase(eb.getServerConnection().getUserId())) {
                rewardedItem.setDefaultCommand(rewardCommand);
                rewardedItem.setItemCommandListener(this);
            }
        }
        append(rewardedItem);


    }

    public void commandAction(Command c, Displayable d) {
        if (c == backCommand) {
            Display.getDisplay(eb).setCurrent(previous);
        }
    }

    public void commandAction(Command c, Item item) {
        if (item == userItem) {
            UserInfoForm uif = new UserInfoForm(eb, this);
            eb.getServerConnection().getUserInfo(uif, uif, this);
        } else if (item == rewardedItem) {
            eb.getServerConnection().giveReward(answer.getId(), this, this, null);
        }
    }

    public void onErrandsList(Vector errands, String command, String[] arguments) {
    }

    public void onViewErrand(Errand errand, String command, String[] arguments) {
    }

    public void onUserInfo(User user, String command, String[] arguments) {
    }

    public void onOK(String command, String[] arguments) {
        rewardedItem.setText("yes");
        rewardedItem.setDefaultCommand(null);
    }

    public void onError(Exception e) {
    }
}
