package com.airgraft.autocomplete.util;

import com.airgraft.autocomplete.model.Coordinates;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AutoCompleteUtils {

    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    /*No instance allowed. Invoke statically.*/
    private AutoCompleteUtils(){}


    public static Double calculate(final Coordinates cityCoordinates, final Coordinates userCoordinates) {
        if(!cityCoordinates.isValid() && !userCoordinates.isValid()){
            return null;
        }
        return measure(userCoordinates.getLatitude(),
                userCoordinates.getLongitude(),
                cityCoordinates.getLatitude(), cityCoordinates.getLongitude());
    }

    private static double measure(double userLat, double userLng, double cityLat, double cityLong) {
        double latDistance = Math.toRadians(userLat - cityLat);
        double lngDistance = Math.toRadians(userLng - cityLong);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(cityLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return  (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }

    public static double score(String query, String cityName, Coordinates coordinates){
     final var distance = LevenshteinDistance.getDefaultInstance().apply(cityName.toLowerCase().trim(), query.toLowerCase().trim());
     final var value = (double) distance / cityName.length();
        double round = distance > query.length() ? 1d - value : value;
        return new BigDecimal(round).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }
}
