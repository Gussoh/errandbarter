/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.errandbarter.server.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Zach
 */
public class Answer implements Serializable {

  
	private Integer id;
    private int errandID;
    private String userID;
    private Date timestamp;
    private double longitude;
    private double latitude;
    private int pointsRewarded;
    private String information;
    
    public Answer() {}
    
    public Answer(Integer id, int errandID, String userID, Date timestamp,
			double longitude, double latitude, int pointsRewarded,
			String information) {
	
		this.id = id;
		this.errandID = errandID;
		this.userID = userID;
		this.timestamp = timestamp;
		this.longitude = longitude;
		this.latitude = latitude;
		this.pointsRewarded = pointsRewarded;
		this.information = information;
	}


	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public int getErrandID() {
        return errandID;
    }

    public void setErrandID(int errandID) {
        this.errandID = errandID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getPointsRewarded() {
        return pointsRewarded;
    }

    public void setPointsRewarded(int pointsRewarded) {
        this.pointsRewarded = pointsRewarded;
    }
    
    
    public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}
}
