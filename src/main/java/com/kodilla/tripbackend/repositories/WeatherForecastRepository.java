package com.kodilla.tripbackend.repositories;

import com.kodilla.tripbackend.domains.WeatherForecast;
import org.springframework.data.repository.CrudRepository;

public interface WeatherForecastRepository extends CrudRepository<WeatherForecast, Long> {
}
