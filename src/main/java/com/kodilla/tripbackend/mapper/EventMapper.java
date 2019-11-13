package com.kodilla.tripbackend.mapper;

import com.kodilla.tripbackend.domains.Event;
import com.kodilla.tripbackend.domains.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    @Autowired
    LocalizationMapper localizationMapper;

    public Event mapToEvent(EventDto eventDTO) {
        return new Event(eventDTO.getDate(), localizationMapper.mapToLocalization(eventDTO.getLocalizationDto()), eventDTO.getPrice());
    }

    public List<EventDto> mapToDtoList(List<Event> events) {
        return events.stream()
                .map(e -> new EventDto(
                        localizationMapper.mapToDto(e.getLocalization()), e.getDate(), e.getPrice(),
                        e.getUsersSignedUp(), e.getCreator(), e.getWeatherForecast())
                ).collect(Collectors.toList());
    }

}
