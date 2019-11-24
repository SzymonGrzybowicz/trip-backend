package com.kodilla.tripbackend.mapper;

import com.kodilla.tripbackend.domains.Trip;
import com.kodilla.tripbackend.domains.TripDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TripMapper {

    @Autowired
    @Setter
    private LocalizationMapper localizationMapper;

    public List<TripDto> mapToDtoList(List<Trip> trips) {
        return trips.stream()
                .map(t ->
                        new TripDto(
                                t.getId(),
                                localizationMapper.mapToDtoList(t.getLocalizations()),
                                t.getDate(),
                                t.getDistance(),
                                t.getWeatherForecast() == null ? null : (int) Math.round(t.getWeatherForecast().getTemperature())))
                .collect(Collectors.toList());
    }

    public Trip mapToTrip(TripDto tripDto) {
        return new Trip(
                tripDto.getDate(),
                tripDto.getDistance(),
                localizationMapper.mapToLocalisationList(tripDto.getLocalizations()));
    }
}
