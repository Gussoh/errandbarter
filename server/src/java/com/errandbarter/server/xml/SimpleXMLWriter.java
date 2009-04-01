/*
 * XMLWriter.java
 *
 * Created on March 18, 2009, 11:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.errandbarter.server.xml;

import com.errandbarter.server.controller.Response;
import com.errandbarter.server.entity.Answer;
import com.errandbarter.server.entity.Errand;
import com.errandbarter.server.entity.User;
import java.util.List;

/**
 *
 * @author Zach
 */
public class SimpleXMLWriter implements XMLWriter {

    private final static SimpleXMLWriter singleton = new SimpleXMLWriter();

    protected SimpleXMLWriter() {
    }

    public static SimpleXMLWriter getInstance() {
        return singleton;
    }

    public String getXML(List<Errand> errands) {

        errands.add(new Errand(21321, 123242, "test", 0.4, 34, "bry", 3.4, 5.6, 23, 324, "tjabba"));

        String errandsListXML = "<errands>\n";

        for (Errand errand : errands) {
            errandsListXML += getXML(errand);
        }

        errandsListXML += "</errands>";

        return errandsListXML;
    }

    public String getXML(Errand errand) {

        if (errand == null) {
            return getXML(new Response(Response.STATUS_ERROR, "Errand does not exist"));
        }

        String errandXML = new String();
        /* "<errand id=\"" + errand.getId()
        + "\" timeout=\"" + errand.getTimeout() + "\" user=\"" + errand.getUser()
        + "\" userReliability=\"" + errand.getUserReliability()
        + "\" price=\"" + errand.getPrice()
        + "\"><location lat=\"" + errand.getLocationLatitude()
        + "\" long=\"" + errand.getLocationLongtitude()
        + "\" range=\"" + errand.getLocationRange()
        + "\" distance=\"" + errand.getDistance()
        + "\">" + errand.getLocation()
        + "</location><description>" + errand.getDescription()
        + "</description></errand>";
         */
        errandXML += "<errand id=\"" + errand.getId() + "\" timeout=\"" + errand.getTimeout() + "\" user=\"" + errand.getUser() + "\" userReliability=\"" + errand.getUserReliability() + "\" price=\"" + errand.getPrice() + "\">\n<location lat=\"" + errand.getLocationLatitude() + "\" long=\"" + errand.getLocationLongtitude() + "\" range=\"" + errand.getLocationRange() + "\" distance=\"" + errand.getDistance() + "\">" + errand.getLocation() + "</location>\n<description>" + errand.getDescription() + "</description>\n<answers>";

        // get list of answers for the errand
        if (errand.getAnswers() != null) {
            for (Answer answer : errand.getAnswers()) {
                errandXML += "<answer id=\"" + answer.getId() + "\" user=\"" + answer.getUserID() + "\" timestamp=\"" + answer.getTimestamp().toString() + "\" lat=\"" + answer.getLatitude() + "\" long=\"" + answer.getLongitude() + "\" pointsRewards=\"" + answer.getPointsRewarded() + "\">" + answer.getInformation() + "</answer>\n";
            }
        }
        errandXML += "</answers>\n</errand>\n";
        return errandXML;
    }

    public String getXML(User user) {

        if (user == null) {
            return getXML(new Response(Response.STATUS_ERROR, "User does not exist"));
        }

        String userXML = "<user id=\"" + user.getId() + "\"><balance>" + user.getBalance() + "</balance><disposablebalance>" + user.getDisposableBalance() + "</disposablebalance><reliability>" + user.getReliability() + "</reliability></user>";
        return userXML;
    }

    public String getXML(Response response) {
        String responseXML = "<response>\n<status>" + response.getStatus() + "</status>\n<message>" + response.getMessage() + "</message>\n</response>";
        return responseXML;
    }
}
