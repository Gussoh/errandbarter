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

import java.util.Date;
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
        Map<String, User> userDB = new Hashtable<String, User>();
        userDB.put("zach", new User("zach", 100, 50, 0.8));
        userDB.put("daniel", new User("daniel", 100, 50, 0.8));
        userDB.put("gustav", new User("gustav", 100, 50, 0.8));
        //lklklkl
        userDAO = new MockUserDAO(userDB);
        
        Map<Integer, Answer> answerDB = new Hashtable<Integer, Answer>();
        /*
         * public Answer(Integer id, int errandID, String userID, Date timestamp,
			double longitude, double latitude, int pointsRewarded,
			String information) {
         */
        answerDB.put(1, new Answer(1, 1, "daniel", new Date(), 1.314, 103.8453, 0, "Amy"));
        answerDAO = new MockAnswerDAO(answerDB);
        
        Map<Integer, Errand> errandDB = new Hashtable<Integer, Errand>();
        /* public Errand(Integer id, long timeout, String user,
			double userReliability, int price, String location,
			double locationLatitude, double locationLongtitude,
			double distance, int locationRange, String description) */
        errandDB.put(1, new Errand(1, 1234567890, "zach", 0.8,
			50, "Orchard Borders", 1.304, 103.8353, 0, 0, " What is the name of the really cute cashier?"));
        
        
        errandDAO = new MockErrandDAO(errandDB, answerDAO);

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