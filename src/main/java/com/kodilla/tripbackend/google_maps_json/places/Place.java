package com.kodilla.tripbackend.google_maps_json.places;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Place {

    @JsonProperty(value = "place_id")
    private String placeId;

    @JsonProperty(value = "structured_formatting")
    private StructuredFormatting structuredFormatting;
}
