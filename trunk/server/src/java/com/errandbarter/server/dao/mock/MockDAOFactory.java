/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.errandbarter.server.dao.mock;

import java.util.List;

/**
 *
 * @author Zach
 */
public interface MockDAOFactory {

   public ErrandDAO getErrandDAO();
   public UserDAO getUserDAO();
   public AnswerDAO getAnswerDAO();

}