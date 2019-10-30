package com.kodilla.tripbackend.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalizationDTO {

    @JsonProperty(value = "google_id")
    private String googleId;

    @JsonProperty(value = "main_description")
    private String mainDescription;

    @JsonProperty(value = "secondary_description")
    private String secondaryDescription;

    @JsonProperty(value = "number_in_trip")
    private int numberInTrip;
}
