package com.airgraft.autocomplete.config;

import com.airgraft.autocomplete.model.City;
import com.airgraft.autocomplete.model.Coordinates;
import com.airgraft.autocomplete.repository.AutoCompleteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AutoCompleteDataConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
    final AutoCompleteRepository autoCompleteRepository;

    public AutoCompleteDataConfig(AutoCompleteRepository autoCompleteRepository) {
        this.autoCompleteRepository = autoCompleteRepository;
    }

    @PostConstruct
    public void init() {
        logger.info("Loading geo data cache....");
        autoCompleteRepository.setCitiesCache(loadObjectList("src/main/resources/data"));
        logger.info("Loaded {} geo data", autoCompleteRepository.getCitiesCache().size());
    }

    public List<City> loadObjectList(String configFilePath) {
        List<City> list = new ArrayList<>();
        try {
           // var path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI());
            Files.list(Paths.get(configFilePath))
                    .filter(s -> s.toString().endsWith(".tsv"))
                    .forEach(path -> {
                         try {
                    final List<City> collect = Files.lines(path).skip(1).map(s -> {
                        final String[] lineArray = s.split("\\t");
                        try {
                            return new City()
                                    .setId(lineArray[0])
                                    .setName(lineArray[1])
                                    .setAlternateName(lineArray[3])
                                    .setCountry(lineArray[8])
                                    .setCoordinates(
                                            new Coordinates()
                                                    .setLatitude(Double.parseDouble(lineArray[4]))
                                                    .setLongitude(Double.parseDouble(lineArray[5])));
                        } catch (Exception e) {
                            logger.warn("Error parsing line -> {} ", s);
                            return null;
                        }
                    }).filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    list.addAll(collect);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
      /*      return Files.lines(path).skip(1).map(s -> {
                final String[] lineArray = s.split("\\t");
                try {
                    return new City()
                            .setId(lineArray[0])
                            .setName(lineArray[1])
                            .setAlternateName(lineArray[3])
                            .setCountry(lineArray[8])
                            .setCoordinates(
                                    new Coordinates()
                                            .setLatitude(Double.parseDouble(lineArray[4]))
                                            .setLongitude(Double.parseDouble(lineArray[5])));
                } catch (Exception e) {
                    logger.warn("Error parsing line -> {} ", s);
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());*/
        } catch (Exception e) {
            logger.error("Error loading cache", e);
        }
        return list;
    }
}
