/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.errandbarter.server.dao.mock;

import com.errandbarter.server.dao.AnswerDAO;
import com.errandbarter.server.dao.DAOFactory;
import com.errandbarter.server.dao.ErrandDAO;
import com.errandbarter.server.dao.UserDAO;
import com.errandbarter.server.entity.Answer;
import com.errandbarter.server.entity.Errand;
import com.errandbarter.server.entity.User;
import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author Zach
 */
public class MockDAOFactory implements DAOFactory {
    
    private final static MockDAOFactory singleton = new MockDAOFactory();
    
    private ErrandDAO errandDAO;
    private UserDAO userDAO;
    private AnswerDAO answerDAO;
    
    protected MockDAOFactory() {
        //init mock DAOs with mock data and populate data (TODO)
        Map errandDB = new Hashtable<Integer, Errand>();
        errandDAO = new MockErrandDAO(errandDB);
        
        Map userDB = new Hashtable<String, User>();
        userDAO = new MockUserDAO(userDB);
        
        Map answerDB = new Hashtable<Integer, Answer>();
        answerDAO = new MockAnswerDAO(answerDB);
    }
    
    public static MockDAOFactory getInstance() {
        return INSTANCE;
    }
    
    public ErrandDAO getErrandDAO() {
        return errandDAO;
    }
    
    public UserDAO getUserDAO() {
        return userDAO;
    }
    
    public AnswerDAO getAnswerDAO() {
        return answerDAO;
    }

}