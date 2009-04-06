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
        userDB.put("zach", new User("zach", 120, 40, 0.8));
        userDB.put("daniel", new User("daniel", 130, 50, 0.8));
        userDB.put("gustav", new User("gustav", 140, 60, 0.8));
        userDB.put("steve", new User("steve", 200, 100, 0.5));
        userDB.put("bill", new User("bill", 300, 200, 0.1));
        userDB.put("larry", new User("larry", 200, 150, 0.6));
        //lklklkl
        userDAO = new MockUserDAO(userDB);
        
        Map<Integer, Answer> answerDB = new Hashtable<Integer, Answer>();
        /*
         * public Answer(Integer id, int errandID, String userID, Date timestamp,
			double longitude, double latitude, int pointsRewarded,
			String information) {
         */
        answerDB.put(1, new Answer(1, 1, "daniel", new Date(), 1.304, 103.8453, 0, "Amy"));
        answerDB.put(2, new Answer(2, 2, "gustav", new Date(), 1.313, 103.8365, 0, "$5 at stall 1"));
        answerDB.put(3, new Answer(3, 2, "daniel", new Date(), 1.313, 103.8365, 0, "$4 at stall 5"));
        answerDB.put(4, new Answer(4, 3, "zach", new Date(), 1.310, 103.8396, 0, "April 1"));
        answerDB.put(5, new Answer(5, 4, "zach", new Date(), 1.310, 103.835, 0, "111"));
        answerDB.put(6, new Answer(6, 4, "gustav", new Date(), 1.310, 103.835, 0, "502"));
        answerDB.put(7, new Answer(7, 4, "bill", new Date(), 1.310, 103.835, 0, "16"));
        answerDB.put(8, new Answer(8, 4, "steve", new Date(), 1.310, 103.835, 0, "175"));
        answerDB.put(9, new Answer(9, 5, "zach", new Date(), 1.310, 103.829, 0, "S$635"));
        answerDB.put(10, new Answer(10, 5, "bill", new Date(), 1.310, 103.835, 0, "S$760 with BF"));
        answerDB.put(11, new Answer(11, 6, "bill", new Date(), 1.289, 103.861, 0, "1am"));
        answerDB.put(12, new Answer(12, 6, "gustav", new Date(), 1.289, 103.861, 0, "10.30pm"));
        answerDB.put(13, new Answer(13, 7, "daniel", new Date(), 1.289, 103.861, 0, "106"));
        answerDB.put(14, new Answer(14, 7, "zach", new Date(), 1.289, 103.861, 0, "111"));
        answerDB.put(15, new Answer(15, 8, "steve", new Date(), 1.304, 103.8453, 0, "Out of stock"));
        answerDB.put(16, new Answer(16, 8, "gustav", new Date(), 1.304, 103.8453, 0, "No. New stock in tmr"));
        answerDAO = new MockAnswerDAO(answerDB);
        
        Map<Integer, Errand> errandDB = new Hashtable<Integer, Errand>();
        /* public Errand(Integer id, long timeout, String user,
			double userReliability, int price, String location,
			double locationLatitude, double locationLongtitude,
			double distance, int locationRange, String description) */
        errandDB.put(1, new Errand(1, 1234567890, "zach", 0.8,
			50, "Orchard Borders", 1.304, 103.8353, 0, 0, " What is the name of the really cute cashier?"));

        errandDB.put(2, new Errand(2, 1234567890, "zach", 0.8,
    			30, "Newton Food ", 1.313695, 103.8365, 0, 0, " How much do the tiger prawns cost?"));
        
        errandDB.put(3, new Errand(3, 1234567890, "gustav", 0.8,
    			30, "Orchard Cineleisure ", 1.31039, 103.83968, 0, 0, "When does the Nike sale start?"));

        errandDB.put(4, new Errand(4, 1234567890, "daniel", 0.8,
    			20, "Mount Elizabeth Hospital ", 1.310525, 103.83524, 0, 0, "Whice bus service goes to Mt. Elizabeth Hospital?"));

        errandDB.put(5, new Errand(5, 1234567890, "larry", 0.6,
    			30, "Hilton Singapore ", 1.310597, 103.82926, 0, 0, "How much does a night at the Hilton cost?"));

        errandDB.put(6, new Errand(6, 1234567890, "steve", 0.5,
    			15, "Singapore Flyer", 1.289969, 103.861666, 0, 0, "What time is the last ride at the Singapore Flyer?"));

        errandDB.put(7, new Errand(7, 1234567890, "steve", 0.5,
    			15, "Singapore Flyer", 1.289969, 103.861666, 0, 0, "What bus service goes to the Singapore Flyer?"));

         errandDB.put(8, new Errand(8, 1234567890, "daniel", 0.8,
			40, "Orchard Borders", 1.304, 103.8353, 0, 0, " Is the Spiderman DVD available?"));

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