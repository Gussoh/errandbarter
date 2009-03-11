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

        for (int i = 0; i < root.getChildCount(); i++) {
            if (root.getChild(i) instanceof Element) {
                Element element = (Element) root.getChild(i);
                if (element.getName().equalsIgnoreCase("balance")) {
                    balance = Integer.parseInt(element.getText(0));
                } else if (element.getName().equalsIgnoreCase("disposablebalance")) {
                    disposableBalance = Integer.parseInt(element.getText(0));
                } else if (element.getName().equalsIgnoreCase("reliability")) {
                    realiability = Double.parseDouble(element.getText(0));
                }
            }
        }

        if (id == null) {
            throw new OperationException("The whois did not return a user id.");
        }

        return new User(id, balance, disposableBalance, realiability);
    }

    public Vector listErrands() {
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

        return null;
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
                    if (d.getChild(i) instanceof Element) {
                        Element element = (Element) d.getChild(i);
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

            return d;

        } catch (IOException e) {
            throw new OperationException(e.getMessage());
        } catch (XmlPullParserException e) {
            throw new OperationException(e.getMessage());
        }
    }
}
