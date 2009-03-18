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

    public List<Errand> findByLocation(double x, double y, int range);

    public List<Errand> findByOwner(String user);
    
    public List<Errand> findByUser(String user);
}
