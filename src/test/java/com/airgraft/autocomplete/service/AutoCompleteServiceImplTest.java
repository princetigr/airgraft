package com.airgraft.autocomplete.service;

import com.airgraft.autocomplete.model.City;
import com.airgraft.autocomplete.model.Coordinates;
import com.airgraft.autocomplete.repository.AutoCompleteRepository;
import com.airgraft.autocomplete.rest.SuggestionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AutoCompleteServiceImplTest {

    private final AutoCompleteRepository autoCompleteRepository = mock(AutoCompleteRepository.class);
    private final AutoCompleteService autoCompleteService = new AutoCompleteServiceImpl(autoCompleteRepository);

    @BeforeEach
    void setMockOutput() {
        when(autoCompleteRepository.getCitiesCache()).thenReturn(Stream.of
                (new City()
                        .setCoordinates(new Coordinates()
                                .setLatitude(23d)
                                .setLongitude(18d))
                        .setAlternateName("ha")
                        .setName("London")
                        .setCountry("CA"))
                .collect(Collectors.toList()));
    }

    @Test
    void testMatch(){
        SuggestionResponse suggestionResponse = autoCompleteService.match("Londo", new Coordinates().setLatitude(43.70011).setLongitude(-79.4163));
        assertThat(suggestionResponse.getSuggestions(), not(empty()));
    }

    @Test
    void testsearch(){
        List<City> cityList = autoCompleteService.search("Lon");
        assertThat(cityList, not(empty()));
    }

}