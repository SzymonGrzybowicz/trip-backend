package com.kodilla.tripbackend.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "WEATHER_FORECASTS")
@NoArgsConstructor
@Getter
public class WeatherForecast {

    public WeatherForecast(Date lastCheckDate, double temperature) {
        this.lastCheckDate = lastCheckDate;
        this.temperature = temperature;
    }

    @Id
    @Column(name = "WEATHER_FORECAST_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "LAST_CHECK_DATE")
    private Date lastCheckDate;

    @Column(name = "TEMPERATURE")
    private double temperature;
}
