package com.kodilla.tripbackend.dark_sky_weather_json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Forecast {

    @JsonProperty("time")
    private long time;

    @JsonProperty("temperatureHigh")
    private double temperature;
}