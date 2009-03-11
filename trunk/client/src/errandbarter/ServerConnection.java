/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package errandbarter;

import java.io.IOException;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;
import org.xmlpull.v1.XmlPullParserException;

/**
 *
 * @author Gussoh
 */
public class ServerConnection {

    public static String address = "http://localhost:8080/";
    public String userId;

    public ServerConnection(String userId) {
        this.userId = userId;
    }

    public User getUserInfo() throws OperationException {
        return getUserInfo(userId);
    }

    public User getUserInfo(String userId) throws OperationException {
        /*
        <user id="olle">
        <balance>340</balance>
        <disposablebalance>130</disposablebalance>
        <reliability>0.743</reliability>
        </user>
         */


        Document d = fetch("whois", new String[]{"user=" + userId});
        int balance = 0;
        int disposableBalance = 0;
        double realiability = 0;
        String id = null;

        Element root = d.getRootElement();
        for (int i = 0; i < root.getAttributeCount(); i++) {
            if (root.getAttributeName(i).equalsIgnoreCase("id")) {
                id = root.getAttributeValue(i);
            }
        }

        try {
            for (int i = 0; i < root.getChildCount(); i++) {
                Element element = root.getElement(i);
                if (element.getName().equalsIgnoreCase("balance")) {
                    balance = Integer.parseInt(element.getText(0));
                } else if (element.getName().equalsIgnoreCase("disposablebalance")) {
                    disposableBalance = Integer.parseInt(element.getText(0));
                } else if (element.getName().equalsIgnoreCase("reliability")) {
                    realiability = Double.parseDouble(element.getText(0));
                }

            }
        } catch (NumberFormatException e) {
            throw new OperationException(e.getMessage());
        }

        if (id == null) {
            throw new OperationException("The whois did not return a user id.");
        }

        return new User(id, balance, disposableBalance, realiability);
    }

    public Vector listErrands() throws OperationException {


        Document d = fetch("list", new String[]{"latitude=3.4", "longitude=4.5"}); // TODO: insert real values

        Element root = d.getRootElement();

        Vector errands = new Vector();

        for (int i = 0; i < root.getChildCount(); i++) {
            Element errandElement = root.getElement(i);
            if (errandElement.getName().equalsIgnoreCase("errand")) {
                errands.addElement(createErrand(root));
            }

        }

        return errands;
    }

    private Vector createAnswers(Element root) throws OperationException {
        /*
        <answers>
        <answer id="2145442" user="Peter" timestamp="xxx" latitude="x" longitude="y" pointsRewarded="0">
        Amy
        </answer>
        </answers>
         */

        Vector answers = new Vector();

        int id = 0;
        String user = null;
        int timestamp = 0;
        double latitude = 0, longitude = 0;
        int pointsRewarded = 0;
        String answer = null;

        for (int i = 0; i < root.getChildCount(); i++) {
            if (root.getElement(i).getName().equalsIgnoreCase("answer")) {
                Element answerElement = root.getElement(i);

                answer = answerElement.getText(0);

                try {
                    for (int j = 0; j < answerElement.getAttributeCount(); j++) {
                        if (answerElement.getAttributeName(j).equalsIgnoreCase("id")) {
                            id = Integer.parseInt(answerElement.getAttributeValue(j));
                        } else if (answerElement.getAttributeName(j).equalsIgnoreCase("user")) {
                            user = answerElement.getAttributeValue(j);
                        } else if (answerElement.getAttributeName(j).equalsIgnoreCase("timestamp")) {
                            timestamp = Integer.parseInt(answerElement.getAttributeValue(j));
                        } else if (answerElement.getAttributeName(j).equalsIgnoreCase("latitude")) {
                            latitude = Double.parseDouble(answerElement.getAttributeValue(j));
                        } else if (answerElement.getAttributeName(j).equalsIgnoreCase("longitude")) {
                            longitude = Double.parseDouble(answerElement.getAttributeValue(j));
                        } else if (answerElement.getAttributeName(j).equalsIgnoreCase("pointsRewarded")) {
                            pointsRewarded = Integer.parseInt(answerElement.getAttributeValue(j));
                        }
                    }
                } catch (NumberFormatException e) {
                    throw new OperationException(e.getMessage());
                }

                Location location = new Location(latitude, longitude);
                answers.addElement(new Answer(id, user, answer, timestamp, location, pointsRewarded));
            }
        }

        return answers;
    }

    private Errand createErrand(Element root) throws OperationException {

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
        int id = 0;
        int timeout = 0;
        String user = null;
        int price = 0;
        Location location = null;
        String description = null;
        Vector answers = null;

        for (int i = 0; i < root.getChildCount(); i++) {
            Element element = root.getElement(i);
            if (element.getName().equalsIgnoreCase("location")) {
                location = createLocation(element);
            } else if (element.getName().equalsIgnoreCase("description")) {
                description = element.getText(0);
            } else if (element.getName().equalsIgnoreCase("answers")) {
                answers = createAnswers(element);
            }
        }

        return new Errand(id, user, location, description, timeout, price, answers);
    }

    private Location createLocation(Element root) throws OperationException {
        /*<location latitude="123.12323" longitude="12.3124" range="300">
        Hotel Ravishankar
        </location>*/

        double latitude = 0, longitude = 0;
        int range = Location.RANGE_UNDEFINED;
        String name = root.getText(0);

        boolean latitudeFound = false, longitudeFound = false;

        try {
            for (int i = 0; i < root.getAttributeCount(); i++) {
                if (root.getAttributeName(i).equalsIgnoreCase("latitude")) {
                    latitudeFound = true;
                    latitude = Double.parseDouble(root.getAttributeValue(i));
                } else if (root.getAttributeName(i).equalsIgnoreCase("longitude")) {
                    longitudeFound = true;
                    longitude = Double.parseDouble(root.getAttributeValue(i));
                } else if (root.getAttributeName(i).equalsIgnoreCase("range")) {
                    range = Integer.parseInt(root.getAttributeValue(i));
                }
            }
        } catch (NumberFormatException e) {
            throw new OperationException(e.getMessage());
        }

        if (latitudeFound && longitudeFound) {
            return new Location(latitude, longitude, range, name);
        } else {
            throw new OperationException("Missing latitude or longitude coordinates.");
        }


    }

    private Document fetch(String path, String[] variables) throws OperationException {
        try {
            StringBuffer sb = new StringBuffer(path);

            for (int i = 0; i < variables.length; i++) {
                if (i == 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                String variable = variables[i];
                sb.append(variable);
            }

            HttpConnection c = (HttpConnection) Connector.open(sb.toString());

            KXmlParser parser = new KXmlParser();
            parser.setInput(c.openInputStream(), "UTF-8");
            Document d = new Document();
            d.parse(parser);
            c.close();

            Element root = d.getRootElement();

            boolean error = false;
            String message = null;
            boolean statusFound = false;

            if (root.getName().equalsIgnoreCase("response")) {
                for (int i = 0; i < d.getChildCount(); i++) {
                    Element element = d.getElement(i);
                    if (element.getName().equalsIgnoreCase("status")) {
                        statusFound = true;
                        String response = element.getText(0);
                        if (!response.equalsIgnoreCase("ok")) {
                            error = true;
                        }
                    } else if (element.getName().equalsIgnoreCase("message")) {
                        message = element.getText(0);
                    }

                }

                if (error) {
                    throw new OperationException(message);
                } else if (!statusFound) {
                    throw new OperationException("Status element not found in response message.");
                }
            }

            return d;

        } catch (IOException e) {
            throw new OperationException(e.getMessage());
        } catch (XmlPullParserException e) {
            throw new OperationException(e.getMessage());
        }
    }
}
