package com.kodilla.tripbackend.google_maps_json.geolocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Result {

    @JsonProperty(value = "geometry")
    Geometry geometry;
}
