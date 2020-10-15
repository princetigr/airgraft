package com.airgraft.autocomplete.service;

import com.airgraft.autocomplete.model.City;
import com.airgraft.autocomplete.model.Coordinates;
import com.airgraft.autocomplete.model.Country;
import com.airgraft.autocomplete.model.Suggestion;
import com.airgraft.autocomplete.repository.AutoCompleteRepository;
import com.airgraft.autocomplete.rest.SuggestionResponse;
import com.airgraft.autocomplete.util.AutoCompleteUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AutoCompleteServiceImpl implements AutoCompleteService {

    final AutoCompleteRepository autoCompleteRepository;

    public AutoCompleteServiceImpl(final AutoCompleteRepository autoCompleteRepository) {
        this.autoCompleteRepository = autoCompleteRepository;
    }

    @Override
    public SuggestionResponse match(final String query, final Coordinates userCoordinates) {
        return new SuggestionResponse().setSuggestions(search(query)
                .stream()
                .peek(city -> city.distanceFrom(userCoordinates))
                .sorted(Comparator.comparingDouble(City::getDistance))
                .map(city ->
                        new Suggestion()
                        .setLatitude(city.getCoordinates().getLatitude())
                        .setLongitude(city.getCoordinates().getLongitude())
                        .setName(String.format("%s %s", city.getName(), Country.valueOf(city.getCountry()).getDescription()))
                        .setScore(AutoCompleteUtils.score(query, city.getName(), userCoordinates)))
                .filter(suggestion -> Objects.nonNull(suggestion.getName()))
                .sorted(Comparator.comparingDouble(Suggestion::getScore).reversed())
                .collect(Collectors.toList()));
    }

    @Override
    public List<City> search(final String name) {
        return autoCompleteRepository.getCitiesCache().stream()
                .filter(city -> city.getName().contains(name) || city.getAlternateName().contains(name))
                .collect(Collectors.toList());

    }

}
