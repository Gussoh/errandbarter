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
import com.errandbarter.server.dao.mock.MockDAOFactory;
import com.errandbarter.server.entity.Errand;
import com.errandbarter.server.entity.User;
import java.util.List;

/**
 *
 * @author Zach
 */
public class SimpleXMLWriter implements XMLWriter {
    
    private final static SimpleXMLWriter singleton = new SimpleXMLWriter();
    
    protected SimpleXMLWriter() {}
    
    public static SimpleXMLWriter getInstance() {
        return singleton;
    }
    
    public String getXML(List<Errand> Errands) {
        return null;
    }
    
    public String getXML(Errand errand) {
        String errandXML = "<errand id=\"" + errand.getId()
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
        return errandXML;
    }
    
    public String getXML(User user) {
        String userXML = "<user id=\"" + user.getId() + "\"><balance>" + user.getBalance()
                + "</balance><disposablebalance>" + user.getDisposableBalance()
                + "</disposablebalance><reliability>" + user.getReliability()
                + "</reliability></user>";
        return userXML;
    }
    
    public String getXML(Response response) {
        String responseXML = "<response><status>" + response.getStatus()
                + "</response><message>" + response.getMessage()
                + "</message></response>";
        return responseXML;
    }
    
}
