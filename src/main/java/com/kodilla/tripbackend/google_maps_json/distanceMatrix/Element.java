package com.kodilla.tripbackend.google_maps_json.distanceMatrix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Element {

    @JsonProperty("distance")
    Distance distance;
}
