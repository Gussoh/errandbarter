/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package errandbarter;

import errandbarter.UI.TransferDisplay;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.location.QualifiedCoordinates;
import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;

/**
 *
 * @author Gussoh
 */
public class ServerConnection {

    private String address;
    private String userId;
    private Display display;
    private ErrandBarter eb;

    public ServerConnection(ErrandBarter eb, String address, String userId) {
        this.userId = userId;
        this.address = address;
        this.eb = eb;
        display = Display.getDisplay(eb);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public String getUserId() {
        return userId;
    }

    public void giveReward(final int id, final DataListener dataListener, final Displayable nextDisplayable, final Displayable onErrorScreen) {
        new TransferOperation(dataListener, nextDisplayable, onErrorScreen) {

            public void transfer() throws Exception {
                String command = "reward";
                String[] arguments = new String[]{"id=" + id};
                fetch(command, arguments);
                dataListener.onOK(command, arguments);
            }
        }.start();
    }

    public void performErrand(final int id, final String answer, final DataListener dataListener, final Displayable nextDisplayable, final Displayable onErrorScreen) {
        // /perform?user=Gussoh?id=123123&latitude=12312323&longitude=123124&answer=Amy // location of user running errand, optional
        new TransferOperation(dataListener, nextDisplayable, onErrorScreen) {

            public void transfer() throws Exception {
                QualifiedCoordinates location = eb.getPositioning().getPosition();
                
                String command = "perform";
                String[] arguments;
                if (location == null) {
                    arguments = new String[]{"id=" + id, "answer=" + answer};
                } else {
                    arguments = new String[]{"id=" + id, "answer=" + answer, "latitude=" + location.getLatitude(), "longitude=" + location.getLongitude()};
                } // TODO: add location
                fetch(command, arguments);
                dataListener.onOK(command, arguments);
            }
        }.start();
    }

    public void getErrand(final int id, final DataListener dataListener, final Displayable nextDisplayable, final Displayable onErrorScreen) {
        new TransferOperation(dataListener, nextDisplayable, onErrorScreen) {

            public void transfer() throws Exception {
                String command = "viewErrand";
                String[] arguments = new String[]{"id=" + id};
                Document d = fetch(command, arguments);
                dataListener.onViewErrand(createErrand(d.getRootElement()), command, arguments);
            }
        }.start();
    }

    public void getUserInfo(final DataListener dataListener, final Displayable nextDisplayable, final Displayable onErrorScreen) {
        getUserInfo(userId, dataListener, nextDisplayable, onErrorScreen);
    }

    public void getUserInfo(final String userId, final DataListener dataListener, final Displayable nextDisplayable, final Displayable onErrorScreen) {
        /*
        <user id="olle">
        <balance>340</balance>
        <disposablebalance>130</disposablebalance>
        <reliability>0.743</reliability>
        </user>
         */

        new TransferOperation(dataListener, nextDisplayable, onErrorScreen) {

            public void transfer() throws Exception {
                String command = "whois";
                String[] arguments = new String[]{"user=" + userId};
                Document d = fetch(command, arguments);

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

                        if (root.getChild(i) instanceof Element) {
                            Element element = root.getElement(i);
                            if (element.getName().equalsIgnoreCase("balance")) {
                                balance = Integer.parseInt(element.getText(0));
                            } else if (element.getName().equalsIgnoreCase("disposablebalance")) {
                                disposableBalance = Integer.parseInt(element.getText(0));
                            } else if (element.getName().equalsIgnoreCase("reliability")) {
                                realiability = Double.parseDouble(element.getText(0));
                            }
                        }

                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    throw new OperationException(e.getMessage());
                }

                if (id == null) {
                    throw new OperationException("The whois did not return a user id.");
                }

                User user = new User(id, balance, disposableBalance, realiability);

                /*if (id.equalsIgnoreCase(userId)) {
                eb.setMe(user); // convenience to always keep the object updated.
                }*/

                dataListener.onUserInfo(user, command, arguments);
            }
        }.start();
    }

    public void listErrandsPerformedBySelf(final DataListener dataListener, final Displayable nextDisplayable, final Displayable onErrorScreen) {
        listErrandsPerformedBy(userId, dataListener, nextDisplayable, onErrorScreen);
    }

    public void listErrandsPerformedBy(final String user, final DataListener dataListener, final Displayable nextDisplayable, final Displayable onErrorScreen) {

        new TransferOperation(dataListener, nextDisplayable, onErrorScreen) {

            public void transfer() throws Exception {
                String command = "listErrandsPerformed";
                String[] arguments = new String[]{"user=" + user};
                Document d = fetch(command, arguments);
                dataListener.onErrandsList(createErrands(d.getRootElement()), command, arguments);
            }
        }.start();

    }

    public void listOwnErrands(final DataListener dataListener, final Displayable nextDisplayable, final Displayable onErrorScreen) {
        listErrandsBy(userId, dataListener, nextDisplayable, onErrorScreen);
    }

    public void listErrandsBy(final String user, final DataListener dataListener, final Displayable nextDisplayable, final Displayable onErrorScreen) {
        new TransferOperation(dataListener, nextDisplayable, onErrorScreen) {

            public void transfer() throws Exception {
                String command = "list";
                String[] arguments = new String[]{"user=" + user};
                Document d = fetch(command, arguments);

                dataListener.onErrandsList(createErrands(d.getRootElement()), command, arguments);
            }
        }.start();
    }

    public void listErrands(final DataListener dataListener, final Displayable nextDisplayable, final Displayable onErrorScreen) {
        new TransferOperation(dataListener, nextDisplayable, onErrorScreen) {

            public void transfer() throws Exception {
                String command = "list";
                QualifiedCoordinates location = eb.getPositioning().getPosition();

                String[] arguments = null;
                if (location == null) {
                    throw new OperationException("Have not yet aquired current position.");
                } else {
                    arguments = new String[]{"latitude=" + location.getLatitude(), "longitude=" + location.getLongitude()};
                } // TODO: add location

                Document d = fetch(command, arguments);

                dataListener.onErrandsList(createErrands(d.getRootElement()), command, arguments);
            }
        }.start();
    }

    private Vector createErrands(Element errandsElement) throws OperationException {

        if (errandsElement.getName().equalsIgnoreCase("errands")) {

            Vector errands = new Vector();

            for (int i = 0; i < errandsElement.getChildCount(); i++) {
                if (errandsElement.getChild(i) instanceof Element) {
                    Element errandElement = errandsElement.getElement(i);
                    if (errandElement.getName().equalsIgnoreCase("errand")) {
                        errands.addElement(createErrand(errandElement));
                    }
                }
            }

            return errands;
        } else {
            throw new OperationException("Did not receive the expected list of errands");
        }
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
            if (root.getChild(i) instanceof Element) {
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
                        e.printStackTrace();
                        throw new OperationException(e.getMessage());
                    }

                    Location location = new Location(latitude, longitude);
                    answers.addElement(new Answer(id, user, answer, timestamp, location, pointsRewarded));
                }
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
        long timeout = 0;
        String user = null;
        int price = 0;
        Location location = null;
        String description = null;
        Vector answers = null;

        for (int i = 0; i < root.getAttributeCount(); i++) {
            if (root.getAttributeName(i).equalsIgnoreCase("id")) {
                id = Integer.parseInt(root.getAttributeValue(i));
            } else if (root.getAttributeName(i).equalsIgnoreCase("user")) {
                user = root.getAttributeValue(i);
            } else if (root.getAttributeName(i).equalsIgnoreCase("price")) {
                price = Integer.parseInt(root.getAttributeValue(i));
            } else if (root.getAttributeName(i).equalsIgnoreCase("timeout")) {
                timeout = Long.parseLong(root.getAttributeValue(i));
            }
        }

        for (int i = 0; i < root.getChildCount(); i++) {
            if (root.getChild(i) instanceof Element) {
                Element element = root.getElement(i);

                if (element.getName().equalsIgnoreCase("location")) {
                    location = createLocation(element);
                } else if (element.getName().equalsIgnoreCase("description")) {
                    description = element.getText(0);
                } else if (element.getName().equalsIgnoreCase("answers")) {
                    answers = createAnswers(element);
                }
            }
        }

        if (user == null || description == null || location == null) {
            throw new OperationException("Could not fetch errand. (unknown user, description or location)");
        }
        return new Errand(id, user, location, description, timeout, price, answers);
    }

    private Location createLocation(Element root) throws OperationException {
        /*<location latitude="123.12323" longitude="12.3124" range="300">
        Hotel Ravishankar
        </location>*/

        double latitude = 0, longitude = 0;
        int range = Location.RANGE_UNDEFINED;
        double distance = Location.DISTANCE_UNDEFINED;
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
                } else if (root.getAttributeName(i).equalsIgnoreCase("distance")) {
                    distance = Double.parseDouble(root.getAttributeValue(i));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new OperationException(e.getMessage());
        }

        if (latitudeFound && longitudeFound) {
            return new Location(latitude, longitude, range, name, distance);
        } else {
            throw new OperationException("Missing latitude or longitude coordinates.");
        }


    }

    private Document fetch(String path, String[] variables) throws Exception {

        Thread.sleep(50); // The emulator is buggy without this. Sometimes no "yes" button is provided.

        StringBuffer sb = new StringBuffer(address);
        sb.append(path);

        if (variables != null) {
            for (int i = 0; i < variables.length; i++) {
                if (i == 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                String variable = variables[i];
                sb.append(variable);
            }
        }

        //System.out.println("URL: " + sb.toString());
        HttpConnection c = (HttpConnection) Connector.open(sb.toString());

        KXmlParser parser = new KXmlParser();
        parser.setInput(c.openInputStream(), "UTF-8");
        Document d = new Document();
        d.parse(parser);
        int responseCode = c.getResponseCode();
        c.close();

        System.out.println("CONNECTION: " + sb.toString());

        if (d.getChildCount() > 0 && responseCode == 200) {
            Element root = d.getRootElement();

            boolean error = false;
            String message = null;
            boolean statusFound = false;

            if (root.getName().equalsIgnoreCase("response")) {
                for (int i = 0; i < d.getChildCount(); i++) {
                    if (root.getChild(i) instanceof Element) {
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
                }

                if (error) {
                    throw new OperationException(message);
                } else if (!statusFound) {
                    throw new OperationException("Status element not found in response message.");
                }
            }
        } else {
            throw new OperationException("Server response contained no data. Response code: " + responseCode);
        }
        return d;
    }

    private abstract class TransferOperation extends Thread {

        private Displayable nextDisplayable,  onErrorScreen;
        private DataListener dataListener;

        public TransferOperation(final DataListener dataListener, final Displayable nextDisplayable, final Displayable onErrorScreen) {
            this.nextDisplayable = nextDisplayable;
            this.onErrorScreen = onErrorScreen;
            this.dataListener = dataListener;
        }

        public abstract void transfer() throws Exception;

        public void run() {
            display.setCurrent(new TransferDisplay(display));
            try {
                transfer();
                if (nextDisplayable != null) { // perhaps the datahandler wants to handle this
                    display.setCurrent(nextDisplayable);
                }
            } catch (Exception e) {
                e.printStackTrace();
                dataListener.onError(e);
                if (onErrorScreen != null) { // should the datalistener handle the error?
                    Alert alert = new Alert("Communication error", e.getMessage(), null, AlertType.ERROR);
                    alert.setTimeout(Alert.FOREVER);
                    display.setCurrent(alert, onErrorScreen);
                } else if(nextDisplayable != null) { // perhaps the datahandler wants to handle this
                    display.setCurrent(nextDisplayable);
                    display.setCurrent(nextDisplayable); // Some strange bug forced me to do this. On second error it didn't go "back" to nextDisplayable
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    display.setCurrent(nextDisplayable);
                }
            }
        }
    }
}
