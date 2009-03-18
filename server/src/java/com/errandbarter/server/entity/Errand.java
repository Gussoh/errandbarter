/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.errandbarter.server.entity;

import java.io.Serializable;

/**
 *
 * @author Zach
 */
public class Errand implements Serializable {

    private Integer id;
    private long timeout;
    private String user;
    private double userReliability;
    private int price;
    private String location;
    private double locationLatitude;
    private double locationLongtitude;
    private int locationRange;
    private String description;

    public Errand() {}
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public double getUserReliability() {
        return userReliability;
    }

    public void setUserReliability(double userReliability) {
        this.userReliability = userReliability;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public double getLocationLongtitude() {
        return locationLongtitude;
    }

    public void setLocationLongtitude(double locationLongtitude) {
        this.locationLongtitude = locationLongtitude;
    }

    public int getLocationRange() {
        return locationRange;
    }

    public void setLocationRange(int locationRange) {
        this.locationRange = locationRange;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }    
    
}
