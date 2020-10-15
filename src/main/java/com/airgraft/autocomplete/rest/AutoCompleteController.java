package com.airgraft.autocomplete.rest;

import com.airgraft.autocomplete.model.Coordinates;
import com.airgraft.autocomplete.service.AutoCompleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutoCompleteController {

    private final Logger logger = LoggerFactory.getLogger(AutoCompleteController.class);
    private final AutoCompleteService autoCompleteService;

    public AutoCompleteController(final AutoCompleteService autoCompleteService) {
        this.autoCompleteService = autoCompleteService;
    }

    @GetMapping("/suggestions")
    public SuggestionResponse suggest(@RequestParam(name = "q") String query,
                                            @RequestParam(required = false) Double latitude,
                                            @RequestParam(required = false) Double longitude){

        logger.debug("Entered suggest with HTTP raw request: {}, {}, {}", query, latitude, longitude );

        final Coordinates coordinates = new Coordinates()
                .setLatitude(latitude)
                .setLongitude(longitude);

        if(coordinates.isValid()){
            logger.info("Using {}", coordinates );
        }
        return autoCompleteService.match(query.toLowerCase().trim(), coordinates);
    }
}
