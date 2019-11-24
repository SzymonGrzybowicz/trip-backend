package com.kodilla.tripbackend.google_maps_json.places;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StructuredFormatting {

    @JsonProperty(value = "main_text")
    private String mainText;

    @JsonProperty(value = "secondary_text")
    private String secondaryText;
}
