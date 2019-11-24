package com.kodilla.tripbackend.service;

import com.kodilla.tripbackend.domains.*;
import com.kodilla.tripbackend.google_maps_json.geolocation.Location;
import com.kodilla.tripbackend.mapper.LocalizationMapper;
import com.kodilla.tripbackend.repositories.EventRepository;
import com.kodilla.tripbackend.repositories.LocalizationRepository;
import com.kodilla.tripbackend.repositories.WeatherForecastRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class EventService {

    @Autowired
    @Setter
    private EventRepository eventRepository;

    @Autowired
    @Setter
    private UserService userService;

    @Autowired
    @Setter
    private LocalizationRepository localizationRepository;

    @Autowired
    @Setter
    private LocalizationMapper localizationMapper;

    @Autowired
    @Setter
    private WeatherForecastRepository weatherRepository;

    @Autowired
    @Setter
    private WeatherService weatherService;

    @Autowired
    @Setter
    private GoogleMapService googleMapService;

    public List<Event> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(e -> checkWeatherIsActualAndUpdate(e))
                .collect(Collectors.toList());
    }

    public List<Event> getEventsCreatedByUser() {
        Optional<User> optionalUser = userService.getCurrentUser();
        if (!optionalUser.isPresent()) {
            return new ArrayList<>();
        }
        return eventRepository.findByCreator(optionalUser.get());
    }

    public List<Event> getEventsUserJoined() {
        Optional<User> optionalUser = userService.getCurrentUser();
        if (!optionalUser.isPresent()) {
            return new ArrayList<>();
        }
        return eventRepository.getEventUserJoined(optionalUser.get().getId());
    }

    public List<Event> getEventsByLocation(String googleId, int radiusInKM) {
        Location location = googleMapService.getCoordinates(googleId);
        return eventRepository.getEventByLocationRadius(
                location.getLongitude(),
                location.getLatitude(),
                radiusInKM
        );
    }

    public boolean saveEvent(Event event) {
        Optional<User> optionalUser = userService.getCurrentUser();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getCreatedEvents().add(event);
            event.setCreator(user);
            eventRepository.save(event);
            return true;
        }
        return false;
    }

    public boolean deleteEvent(Long eventId) {
        Optional<User> optionalUser = userService.getCurrentUser();
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (!optionalEvent.isPresent() || !optionalUser.isPresent()) {
            return false;
        }
        Event event = optionalEvent.get();
        User user = optionalUser.get();
        if (!event.getCreator().equals(user)) {
            return false;
        }
        localizationRepository.delete(event.getLocalization());
        eventRepository.delete(event);
        return true;
    }

    public boolean updateEvent(EventDto eventDto) {
        Optional<Event> optionalEvent = eventRepository.findById(eventDto.getId());
        if (!optionalEvent.isPresent()) return false;

        Event event = optionalEvent.get();
        event.setLocalization(localizationMapper.mapToLocalization(eventDto.getLocalizationDto()));
        event.setDate(eventDto.getDate());
        eventRepository.save(event);
        return true;
    }

    private Event checkWeatherIsActualAndUpdate(Event event) {
        final WeatherForecast forecast = event.getWeatherForecast();
        if (event.getDate().getTime() < System.currentTimeMillis() || event.getDate().getTime() - System.currentTimeMillis() > TimeUnit.DAYS.toMillis(7)) {
            return event;
        }

        if (forecast == null || System.currentTimeMillis() - forecast.getLastCheckDate().getTime() > TimeUnit.DAYS.toDays(1)) {
            if (forecast != null) weatherRepository.delete(forecast);
            {
                event.setWeatherForecast(weatherService.getForecastForDate(
                        event.getDate(),
                        event.getLocalization().getLatitude(),
                        event.getLocalization().getLongitude()));
            }
        }
        eventRepository.save(event);
        return event;
    }
}
