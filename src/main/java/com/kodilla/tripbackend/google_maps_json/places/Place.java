package com.kodilla.tripbackend.google_maps_json.places;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    @JsonProperty(value = "place_id")
    private String placeId;

    @JsonProperty(value = "structured_formatting")
    private StructuredFormatting structuredFormatting;
}
