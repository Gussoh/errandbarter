/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.errandbarter.server.dao.mock;

import com.errandbarter.server.dao.ErrandDAO;
import com.errandbarter.server.entity.Errand;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author Zach
 */
public class MockErrandDAO implements ErrandDAO {

    private Hashtable<Integer, Errand> errands;

    public MockErrandDAO(Hashtable<Integer, Errand> errands) {
        this.errands = errands;
    }

    public Errand findById(Integer id) {
        return errands.get(id);
    }

    public List<Errand> findAll() {
        return new ArrayList<Errand>(errands.values());
    }

    public void makePersistent(Errand errand) {
        if (errand.getId() == null) {
            errand.setId(errands.size() + 1);
        }
        errands.put(errand.getId(), errand);
    }

    public List<Errand> findByLocation(double longitude, double latitude, int range) {
        List<Errand> results = new ArrayList<Errand>();

        for (Errand errand : errands.values()) {
            if (errand.getLocationLongtitude() == longitude && errand.getLocationLatitude() == latitude && errand.getLocationRange() == range) {
                results.add(errand);
            }
        }
        
        return results;
    }

    public List<Errand> findByOwner(String user) {
        List<Errand> results = new ArrayList<Errand>();

        for (Errand errand : errands.values()) {
            String owner = errand.getUser();
            if (owner != null && owner.equals(user)) {
                results.add(errand);
            }
        }

       return results;
    }

    public List<Errand> findByUser(String user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}