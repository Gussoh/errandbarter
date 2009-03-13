/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package errandbarter;

/**
 *
 * @author Gussoh
 */
public class Answer {
    /*
    <answers>
    <answer id="2145442" user="Peter" timestamp="xxx" latitude="x" longitude="y" pointsRewarded="0">
    Amy
    </answer>
    </answers>
     */

    private int id;
    private String user;
    private String answer;
    private int timestamp;
    private Location location;
    private int pointsRewarded;

    public Answer(int id, String user, String answer, int timestamp, Location location, int pointsRewarded) {
        this.id = id;
        this.user = user;
        this.answer = answer;
        this.timestamp = timestamp;
        this.location = location;
        this.pointsRewarded = pointsRewarded;
    }

    public String getAnswer() {
        return answer;
    }
    
    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public int getPointsRewarded() {
        return pointsRewarded;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public String toString() {
        return "Answer id: " + id + ", user: " + user + ", answer: " + answer + ", time: " + timestamp + ", location: " + location + ", points rewarded: " + pointsRewarded;
    }
}
