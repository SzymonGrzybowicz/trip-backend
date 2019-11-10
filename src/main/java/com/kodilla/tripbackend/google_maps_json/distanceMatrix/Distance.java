package com.kodilla.tripbackend.google_maps_json.distanceMatrix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Distance {

    @JsonProperty("text")
    String distanceInKm;
}
