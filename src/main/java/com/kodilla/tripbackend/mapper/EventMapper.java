package com.kodilla.tripbackend.mapper;

import com.kodilla.tripbackend.domains.Event;
import com.kodilla.tripbackend.domains.EventDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    @Autowired
    @Setter
    private LocalizationMapper localizationMapper;

    public Event mapToEvent(EventDto eventDTO) {
        return new Event(eventDTO.getDate(), localizationMapper.mapToLocalization(eventDTO.getLocalizationDto()), eventDTO.getPrice());
    }

    public List<EventDto> mapToDtoList(List<Event> events) {
        return events.stream()
                .map(e -> new EventDto(
                        e.getId(), localizationMapper.mapToDto(e.getLocalization()), e.getDate(),
                        e.getWeatherForecast() == null ? null : (int) Math.round(e.getWeatherForecast().getTemperature()),
                        e.getPrice()))
                .collect(Collectors.toList());
    }

}
