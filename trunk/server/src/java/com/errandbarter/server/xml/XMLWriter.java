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
public interface XMLWriter {
    
    public String getXML(List<Errand> Errands);
    public String getXML(Errand errand);
    public String getXML(User user);
    public String getXML(Response response);

}
