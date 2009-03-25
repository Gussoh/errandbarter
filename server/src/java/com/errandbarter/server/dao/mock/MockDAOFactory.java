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

	private final static MockDAOFactory INSTANCE = new MockDAOFactory();

	private ErrandDAO errandDAO;
	private UserDAO userDAO;
	private AnswerDAO answerDAO;

	protected MockDAOFactory() {
        //init mock DAOs with mock data and populate data (TODO)
        Map<Integer, Errand> errandDB = new Hashtable<Integer, Errand>();
        errandDB.put(1, new Errand(1, 1234567890, "zach", "0.8"
			50, "Hotel Ravishankar", 0, 0, 0, 0, " What is the name of the really cute waitress in the bar?"
			double locationLatitude, double locationLongtitude,
			double distance, int locationRange, String description)
        errandDAO = new MockErrandDAO(errandDB);
        

        Map<String, User> userDB = new Hashtable<String, User>();
        userDB.put("zach", new User("zach", 100, 50, 0.8));
        userDB.put("daniel", new User("daniel", 100, 50, 0.8));
        userDB.put("gustav", new User("gustav", 100, 50, 0.8));
        userDAO = new MockUserDAO(userDB);
        
        Map<Integer, Answer> answerDB = new Hashtable<Integer, Answer>();
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