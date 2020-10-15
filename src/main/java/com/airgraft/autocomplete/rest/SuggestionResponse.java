package com.airgraft.autocomplete.rest;


import com.airgraft.autocomplete.model.Suggestion;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuggestionResponse implements Serializable {
    List<Suggestion> suggestions = new ArrayList<>();

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public SuggestionResponse setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
        return this;
    }

}
