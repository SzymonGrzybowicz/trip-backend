package com.kodilla.tripbackend.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
public class EventDto {

    public EventDto(LocalizationDto localization, Date date, double price, List<User> usersSignedUp, User creator, WeatherForecast weatherForecast) {
        this.localizationDto = localizationDto;
        this.date = date;
        this.price = price;
        this.usersSignedUp = usersSignedUp;
        this.creator = creator;
        this.weatherForecast = weatherForecast;
    }

    private Long id;
    private LocalizationDto localizationDto;
    private Date date;
    private double price;
    private List<User> usersSignedUp;
    private User creator;
    private WeatherForecast weatherForecast;
}
