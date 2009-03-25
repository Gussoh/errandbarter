/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.errandbarter.server.dao.mock;

import com.errandbarter.server.dao.AnswerDAO;
import com.errandbarter.server.dao.ErrandDAO;
import com.errandbarter.server.entity.Answer;
import com.errandbarter.server.entity.Errand;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Zach
 */
public class MockErrandDAO implements ErrandDAO {

	private Map<Integer, Errand> errands;
	private AnswerDAO answerDAO;

	public MockErrandDAO(Map<Integer, Errand> errands) {
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

	public List<Errand> findByLocation(double longitude, double latitude,
			int range) {
		List<Errand> results = new ArrayList<Errand>();

		for (Errand errand : errands.values()) {
			double distance = getDistance(longitude, latitude, 
					errand.getLocationLongtitude(), errand.getLocationLatitude());
			errand.setDistance(distance);
			if(distance < range) results.add(errand);
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

	/* return errands performed by a user */
	public List<Errand> findByUserPerformed(String user) {

		List<Errand> results = new ArrayList<Errand>();

		for (Errand errand : errands.values()) {
			// get answers
			errand.setAnswers(answerDAO.findByErrand(errand.getId()));
			for (Answer answer : errand.getAnswers()) {
				if (answer.getUserID().equalsIgnoreCase(user)) {
					results.add(errand);
					break;
				}
			}
		}
		return results;
	}

	public void setAnswerDAO(AnswerDAO answerDAO) {
		this.answerDAO = answerDAO;
	}

	private double getDistance(double long_a, double lat_a, double long_b,
			double lat_b) {
		return 69.1 * (lat_b - lat_a) * 69.1 * (lat_b - lat_a) + 69.1
				* (long_b - long_a) * Math.cos(lat_a / 57.3) * 69.1
				* (long_b - long_a) * Math.cos(lat_a / 57.3);

	}

}