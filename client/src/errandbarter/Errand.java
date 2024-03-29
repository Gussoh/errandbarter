/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package errandbarter;

import java.util.Vector;

/**
 *
 * @author Gussoh
 */
public class Errand {
    /*
    <errands>

    <errand id="123123" timeout="1234567890" user="gussoh" price="50">
    // unix timestamp of timeout

    <location latitude="123.12323" longitude="12.3124" range="300">
    Hotel Ravishankar
    </location>
    // range in meters, text data is arbitrary user input

    <description>
    What is the name of the really cute waitress in the bar?
    </description>

    <answers>
    <answer id="2145442" user="Peter" timestamp="xxx" latitude="x" longitude="y" pointsRewarded="0">
    Amy
    </answer>
    </answers>

    </errand>

    <errand ...>...< /> ...

    </errands>

     */

    private int id;
    private String user;
    private Location location;
    private String description;
    private long timeout;
    private int price;
    private Vector answers;

    public Errand(int id, String user, Location location, String description, long timeout, int price, Vector answers) {
        this.id = id;
        this.user = user;
        this.location = location;
        this.description = description;
        this.timeout = timeout;
        this.price = price;
        this.answers = answers;
    }

    public Errand(int id, String user, Location location, String description, int timeout, int price) {
        this.id = id;
        this.user = user;
        this.location = location;
        this.description = description;
        this.timeout = timeout;
        this.price = price;
    }

    /**
     * Calculates if a errand has been rewarded by looking at the answers
     * @return true if it is rewarded
     */
    public boolean isRewarded() {
        boolean rewarded = false;
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = (Answer) answers.elementAt(i);
            if (answer.getPointsRewarded() > 0) {
                rewarded = true;
                break;
            }
        }
        return rewarded;
    }

    public Vector getAnswers() {
        return answers;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public int getPrice() {
        return price;
    }

    public long getTimeout() {
        return timeout;
    }

    public String getUser() {
        return user;
    }

    public String toString() {
        return "Errand id: " + id + ", user: " + user + ", location: " + location + ", description: " + description + ", timeout: " + timeout + ", price: " + price + ", answers: " + answers;
    }
}
