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
import com.errandbarter.server.entity.Errand;
import com.errandbarter.server.entity.User;
import java.util.List;

/**
 *
 * @author Zach
 */
public class SimpleXMLWriter implements XMLWriter {

    public String getXML(List<Errand> Errands) {
        return null;
    }

    public String getXML(Errand errand) {
        return null;
    }

    public String getXML(User user) {
        return null;
    }

    public String getXML(Response response) {
        return null;
    }
       
}
