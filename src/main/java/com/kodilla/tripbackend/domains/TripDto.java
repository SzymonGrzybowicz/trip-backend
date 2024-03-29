package com.kodilla.tripbackend.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TripDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("localizations")
    private List<LocalizationDto> localizations;

    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date date;

    @JsonProperty("distance")
    private double distance;

    @JsonProperty("temperature")
    private Integer temperature;
}
