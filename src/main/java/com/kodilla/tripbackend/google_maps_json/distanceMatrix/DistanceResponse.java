package com.kodilla.tripbackend.google_maps_json.distanceMatrix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class DistanceResponse {

    @JsonProperty("rows")
    List<Row> rows;
}