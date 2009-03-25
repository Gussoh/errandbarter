package com.errandbarter.server.dao;

import com.errandbarter.server.entity.Errand;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Zach
 */
public interface ErrandDAO extends GenericDAO<Errand, Integer> {

    public List<Errand> findByLocation(double longitude, double latitude, int range);

    public List<Errand> findByOwner(String user);
    
    public List<Errand> findByUserPerformed(String user);
}
