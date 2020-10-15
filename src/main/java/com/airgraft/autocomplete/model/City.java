package com.airgraft.autocomplete.model;


import com.airgraft.autocomplete.util.AutoCompleteUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class City{

    private String id;
    private String name;
    private String alternateName;
    private Coordinates coordinates;
    private String country;
    private Double distance;

    public String getId() {
        return id;
    }

    public City setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public City setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlternateName() {
        return alternateName;
    }

    public City setAlternateName(String alternateName) {
        this.alternateName = alternateName;
        return this;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public City setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public City setCountry(String country) {
        this.country = country;
        return this;
    }

    public Double getDistance() {
        return distance;
    }

    public City setDistance(Double distance) {
        this.distance = distance;
        return this;
    }

    @JsonIgnore
    public void distanceFrom(final Coordinates userCoordinates) {
        if(!userCoordinates.isValid()){
            distance= Double.MAX_VALUE;
            return;
        }
        distance = AutoCompleteUtils.calculate(getCoordinates(), userCoordinates);
    }
}
