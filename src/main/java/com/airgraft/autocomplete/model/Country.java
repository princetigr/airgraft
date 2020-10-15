package com.airgraft.autocomplete.model;

public enum Country {

    CA("Canada"),
    US("United States");

    private final String desc;

    Country(final String desc) {
        this.desc = desc;
    }

    public final String getDescription(){
        return this.desc;
    }
}
