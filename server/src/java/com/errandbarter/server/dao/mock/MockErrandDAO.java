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

	private static final double DEFAULT_RANGE = 1; 
	
	public MockErrandDAO(Map<Integer, Errand> errands, AnswerDAO answerDAO) {
		this.errands = errands;
		this.answerDAO = answerDAO;
	}

	public Errand findById(Integer id) {
		return setAnswers(errands.get(id));
	}

	public List<Errand> findAll() {
		return setAnswers(new ArrayList<Errand>(errands.values()));
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
			
			if(range == 0) {
				if(errand.getLocationRange() == 0) {
					if(errand.getDistance() <= DEFAULT_RANGE) results.add(errand);
				} else {
					if(errand.getDistance() <= errand.getLocationRange()) results.add(errand);
				}
				
			} else {
				if(distance < range) results.add(errand);
			}
		}
		return setAnswers(results);
	}

	public List<Errand> findByOwner(String user) {
		List<Errand> results = new ArrayList<Errand>();

		for (Errand errand : errands.values()) {
			String owner = errand.getUser();
			if (owner != null && owner.equals(user)) {
				results.add(errand);
			}
		}

		return setAnswers(results);
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
		return setAnswers(results);
	}

	public void setAnswerDAO(AnswerDAO answerDAO) {
		this.answerDAO = answerDAO;
	}

    // calculates the spherical distance in kilometres between 2 latitude, longitude coordinates
    // code adapted from The Android Open Source Project - GlobalTime.java - samples/GlobalTime/src/com/android/globaltime
	private double getDistance(double lat_a, double long_a, double lat_b, double long_b) {
		
      	// convert coordinates from degrees to radians
        lat_a /= 57.29578;
        lat_b /= 57.29578;
        long_a /= 57.29578;
        long_b /= 57.29578;

        double r = 6371.0; // Radius of the earth in km
        double latDiff = lat_b - lat_a;
        double lonDiff = long_b - long_a;
        double sinlat2 = Math.sin(latDiff / 2);
        sinlat2 *= sinlat2;
        double sinlon2 = Math.sin(lonDiff / 2);
        sinlon2 *= sinlon2;

        double a = sinlat2 + Math.cos(lat_a) * Math.cos(lat_b) * sinlon2;
        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return r * c;

        /*return 69.1 * (lat_b - lat_a) * 69.1 * (lat_b - lat_a) + 69.1
				* (long_b - long_a) * Math.cos(lat_a / 57.3) * 69.1
				* (long_b - long_a) * Math.cos(lat_a / 57.3);*/

	}
	
	private Errand setAnswers(Errand errand) {
		errand.setAnswers(answerDAO.findByErrand(errand.getId()));
		return errand;
	}

	private List<Errand> setAnswers(List<Errand> errands) {
		for(Errand errand: errands) 
			errand.setAnswers(answerDAO.findByErrand(errand.getId()));
		return errands;
	}

}