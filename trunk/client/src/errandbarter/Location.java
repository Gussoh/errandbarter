/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package errandbarter;

import javax.microedition.location.QualifiedCoordinates;

/**
 *
 * @author Gussoh
 */
public class Location {

    public static final int RANGE_UNDEFINED = -1;
    public static final double DISTANCE_UNDEFINED = -1.0;

    private double latitude, longitude, distance;
    private int range;
    private String name = null;



    public Location(double latitude, double longitude, int range, String name, double distance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.range = range;
        this.name = name;
        this.distance = distance;
    }

    public Location(double latitude, double longitude, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.range = RANGE_UNDEFINED;
        this.distance = DISTANCE_UNDEFINED;
    }

    public Location(double latitude, double longitude, int range) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.range = range;
        this.distance = DISTANCE_UNDEFINED;
    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        range = RANGE_UNDEFINED;
        this.distance = DISTANCE_UNDEFINED;
        
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getRange() {
        return range;
    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }

    

    public String toString() {
        return "Location: lat: " + latitude + ", long: " + longitude + ", range: " + range + ", name: " + name;
    }
}
