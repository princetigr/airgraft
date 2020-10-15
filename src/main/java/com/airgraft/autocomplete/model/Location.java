package com.airgraft.autocomplete.model;

/**
Represents a location relative to a user's given coordinates.
 */
public class Location  {

    private City city;
    private double distance;
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
