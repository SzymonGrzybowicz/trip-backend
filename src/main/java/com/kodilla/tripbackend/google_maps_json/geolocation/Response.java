package com.kodilla.tripbackend.google_maps_json.geolocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Response {

    @JsonProperty(value = "results")
    List<Result> results;
}
