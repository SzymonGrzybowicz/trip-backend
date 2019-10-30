package com.kodilla.tripbackend.google_maps_json.geolocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Location {

    @JsonProperty(value = "lat")
    private double latitude;

    @JsonProperty(value = "lng")
    private double longitude;
}
