package com.airgraft.autocomplete.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Coordinates {
    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public Coordinates setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Coordinates setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    @JsonIgnore
    public boolean isValid() {
        return Objects.nonNull(getLatitude()) && Objects.nonNull(getLongitude());
    }
}
