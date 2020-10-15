package com.airgraft.autocomplete.service;

import com.airgraft.autocomplete.model.City;
import com.airgraft.autocomplete.model.Coordinates;
import com.airgraft.autocomplete.rest.SuggestionResponse;

import java.util.List;

public interface AutoCompleteService {

     SuggestionResponse match(final String query, final Coordinates coordinates);
     List<City> search (final String name);
}
