package com.kodilla.tripbackend.dark_sky_weather_json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Forecast {

    @JsonProperty("time")
    private long time;

    @JsonProperty("temperatureHigh")
    private double temperature;
}
