package com.airgraft.autocomplete.config;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class Debug {

    public static void main(String[] args) {
    String city="Solon";
    String query="lon";
        var distance = LevenshteinDistance.getDefaultInstance().apply(query.toLowerCase(), city.toLowerCase());

        System.out.println(distance);

        System.out.println(1d-(double)distance/city.length());
    }
}
