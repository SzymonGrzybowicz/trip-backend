package com.kodilla.tripbackend.dark_sky_weather_json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Daily {

    @JsonProperty("data")
    private List<Forecast> data;
}
