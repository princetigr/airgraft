package com.airgraft.autocomplete.model;

public class Suggestion {

    private String name;
    private Double latitude;
    private Double longitude;
    private Double score;

    public String getName() {
        return name;
    }

    public Suggestion setName(String name) {
        this.name = name;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Suggestion setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Suggestion setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public Double getScore() {
        return score;
    }

    public Suggestion setScore(Double score) {
        this.score = score;
        return this;
    }
}
