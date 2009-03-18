/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.errandbarter.server.dao;

import java.util.List;

/**
 *
 * @author Zach
 */
public interface GenericDAO<T, ID> {

    T findById(ID id);

    List<T> findAll();

    void makePersistent(T entity);

}