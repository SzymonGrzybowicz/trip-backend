package com.kodilla.tripbackend.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class EventDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("localization")
    private LocalizationDto localizationDto;

    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date date;

    @JsonProperty("temperature")
    private Integer temperature;

    @JsonProperty("price")
    private double price;
}
