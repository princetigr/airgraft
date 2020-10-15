package com.airgraft.autocomplete.util;

import com.airgraft.autocomplete.model.Coordinates;
import org.junit.jupiter.api.Test;

class AutoCompleteUtilsTest {

    @Test
    void calculate() {
//suggestions?q=Londo&latitude=43.70011&longitude=-79.4163
        //48.9555947,-93.7022655
        final Coordinates toronto =  new Coordinates()
                .setLatitude(48.9555947)
                .setLongitude(-93.7022655);

        final Coordinates vancouver =  new Coordinates()
                .setLatitude(49.2577143)
                .setLongitude(-123.1939434);


        System.out.println(AutoCompleteUtils.calculate(toronto, vancouver));

    }
}