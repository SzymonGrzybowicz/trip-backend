package com.kodilla.tripbackend.google_maps_json.places;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodilla.tripbackend.google_maps_json.places.Place;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Prediction {

    public Prediction() {
        this.places = new ArrayList<>();
    }

    @JsonProperty("predictions")
    private List<Place> places;
}
