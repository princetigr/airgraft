package com.airgraft.autocomplete.repository;

import com.airgraft.autocomplete.model.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoCompleteRepository {

    private List<City> citiesCache;

    public  List<City> getCitiesCache() {
        return citiesCache;
    }

    public void setCitiesCache(List<City> citiesCache) {
        this.citiesCache = citiesCache;
    }
}
