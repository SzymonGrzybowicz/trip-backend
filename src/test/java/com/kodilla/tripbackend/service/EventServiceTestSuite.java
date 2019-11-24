package com.kodilla.tripbackend.service;

import com.kodilla.tripbackend.domains.*;
import com.kodilla.tripbackend.google_maps_json.geolocation.Location;
import com.kodilla.tripbackend.mapper.LocalizationMapper;
import com.kodilla.tripbackend.repositories.EventRepository;
import com.kodilla.tripbackend.repositories.LocalizationRepository;
import com.kodilla.tripbackend.repositories.WeatherForecastRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTestSuite {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserService userService;

    @Mock
    private LocalizationRepository localizationRepository;

    @Mock
    private LocalizationMapper localizationMapper;

    @Mock
    private WeatherForecastRepository weatherRepository;

    @Mock
    private WeatherService weatherService;

    @Mock
    private GoogleMapService googleMapService;

    private EventService eventService;
    private  Event event;

    @Before
    public void init() {
        Mockito.lenient().doNothing().when(weatherRepository).delete(Mockito.any());
        Mockito.lenient().when(weatherService.getForecastForDate(Mockito.any(), Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(null);
        User user = new User("aaa", "bbb", true);
        event = new Event(1l, new Localization("","", 0.0, 1.1,0,""),
                new Date(200l), 20.0, new ArrayList<>(), user, null);
        Mockito.lenient().when(userService.getCurrentUser()).thenReturn(Optional.of(user));
        Mockito.lenient().when(googleMapService.getCoordinates(Mockito.any())).thenReturn(new Location(0.0, 1.1));
        eventService = new EventService();
        eventService.setEventRepository(eventRepository);
        eventService.setUserService(userService);
        eventService.setLocalizationRepository(localizationRepository);
        eventService.setLocalizationMapper(localizationMapper);
        eventService.setWeatherRepository(weatherRepository);
        eventService.setWeatherService(weatherService);
        eventService.setGoogleMapService(googleMapService);
    }

    @Test
    public void getAllEvents() {
        //Given
        List<Event> events = new ArrayList<>();
        events.add(event);
        Mockito.when(eventRepository.findAll()).thenReturn(events);
        //When
        List<Event> result = eventService.getAllEvents();
        //Then
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(Long.valueOf(1), result.get(0).getId());
        Assert.assertEquals(200l, result.get(0).getDate().getTime());
    }

    @Test
    public void getEventsCreatedByUser() {
        List<Event> events = new ArrayList<>();
        events.add(event);
        Mockito.when(eventRepository.findByCreator(Mockito.any())).thenReturn(events);
        //When
        List<Event> result = eventService.getEventsCreatedByUser();
        //Then
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(Long.valueOf(1), result.get(0).getId());
        Assert.assertEquals(200l, result.get(0).getDate().getTime());
    }

    @Test
    public void getEventsUserJoined() {
        List<Event> events = new ArrayList<>();
        events.add(event);
        Mockito.when(eventRepository.getEventUserJoined(Mockito.any())).thenReturn(events);
        //When
        List<Event> result = eventService.getEventsUserJoined();
        //Then
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(Long.valueOf(1), result.get(0).getId());
        Assert.assertEquals(200l, result.get(0).getDate().getTime());
    }

    @Test
    public void getEventsByLocation() {
        List<Event> events = new ArrayList<>();
        events.add(event);
        Mockito.when(eventRepository.getEventByLocationRadius(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyInt())).thenReturn(events);
        //When
        List<Event> result = eventService.getEventsByLocation("aa", 100);
        //Then
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(Long.valueOf(1), result.get(0).getId());
        Assert.assertEquals(200l, result.get(0).getDate().getTime());
    }

    @Test
    public void saveEvent() {
        //Given
        Mockito.when(eventRepository.save(Mockito.any())).thenReturn(event);
        //When
        eventService.saveEvent(event);
        //Then
        Mockito.verify(eventRepository).save(event);
    }

    @Test
    public void deleteEvent() {
        //Given
        Mockito.when(eventRepository.findById(1l)).thenReturn(Optional.of(event));
        //When
        boolean result = eventService.deleteEvent(1l);
        //Then
        Assert.assertEquals(true, result);
    }

    @Test
    public void updateEvent() {
        //Given
        Mockito.when(eventRepository.findById(1l)).thenReturn(Optional.of(event));
        EventDto eventDto = new EventDto(1l, new LocalizationDto("", "", "",0),
                new Date(200l), 10, 10.2);
        //When
        boolean result = eventService.updateEvent(eventDto);
        //Then
        Assert.assertEquals(true, result);
    }
}