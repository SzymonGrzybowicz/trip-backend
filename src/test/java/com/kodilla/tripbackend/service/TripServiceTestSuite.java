package com.kodilla.tripbackend.service;

import com.kodilla.tripbackend.domains.Trip;
import com.kodilla.tripbackend.domains.User;
import com.kodilla.tripbackend.domains.WeatherForecast;
import com.kodilla.tripbackend.google_maps_json.geolocation.Location;
import com.kodilla.tripbackend.mapper.LocalizationMapper;
import com.kodilla.tripbackend.repositories.LocalizationRepository;
import com.kodilla.tripbackend.repositories.TripRepository;
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
public class TripServiceTestSuite {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private LocalizationMapper localizationMapper;

    @Mock
    private LocalizationRepository localizationRepository;

    @Mock
    private WeatherService weatherService;

    @Mock
    private WeatherForecastRepository weatherRepository;

    @Mock
    private GoogleMapService googleMapService;

    @Mock
    private UserService userService;

    @Mock
    private User user;

    private TripService tripService;

    @Before
    public void init() {
        tripService = new TripService();
        tripService.setTripRepository(tripRepository);
        tripService.setLocalizationMapper(localizationMapper);
        tripService.setLocalizationRepository(localizationRepository);
        tripService.setWeatherService(weatherService);
        tripService.setWeatherRepository(weatherRepository);
        tripService.setGoogleMapService(googleMapService);
        tripService.setUserService(userService);
        Mockito.lenient().doNothing().when(weatherRepository).delete(Mockito.any());
        Mockito.lenient().when(weatherService.getForecastForDate(Mockito.any(), Mockito.anyDouble(), Mockito.anyDouble()))
                .thenReturn(new WeatherForecast(new Date(200l), 20.0));
        Mockito.lenient().when(user.getId()).thenReturn(1l);
        Mockito.lenient().when(userService.getCurrentUser()).thenReturn(Optional.of(user));
        Mockito.lenient().when(tripRepository.save(Mockito.any())).thenReturn(new Trip());
        Mockito.lenient().doNothing().when(localizationRepository).delete(Mockito.any());
    }

    @Test
    public void getAllTrips() {
        //Given
        Trip trip1 = new Trip(new Date(200l), 200.0, new ArrayList<>());
        Trip trip2 = new Trip(new Date(400l), 300.0, new ArrayList<>());
        Trip trip3 = new Trip(new Date(300l), 400.0, new ArrayList<>());
        List<Trip> trips = new ArrayList<>();
        trips.add(trip1);
        trips.add(trip2);
        trips.add(trip3);
        Mockito.when(tripRepository.findAll()).thenReturn(trips);
        //When
        List<Trip> result = tripService.getAllTrips();
        //Then
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(200l, result.get(0).getDate().getTime());
        Assert.assertEquals(300.0, result.get(1).getDistance(), 0.1);
    }

    @Test
    public void getTripsCreatedByUser() {
        //Given
        Trip trip1 = new Trip(new Date(200l), 200.0, new ArrayList<>());
        Trip trip2 = new Trip(new Date(400l), 300.0, new ArrayList<>());
        Trip trip3 = new Trip(new Date(300l), 400.0, new ArrayList<>());
        List<Trip> trips = new ArrayList<>();
        trips.add(trip1);
        trips.add(trip2);
        trips.add(trip3);
        Mockito.when(tripRepository.findByCreator(Mockito.any())).thenReturn(trips);
        //When
        List<Trip> result = tripService.getTripsCreatedByUser();
        //Then
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(200l, result.get(0).getDate().getTime());
        Assert.assertEquals(300.0, result.get(1).getDistance(), 0.1);
    }

    @Test
    public void getTripsUserJoined() {
        //Given
        Trip trip1 = new Trip(new Date(200l), 200.0, new ArrayList<>());
        Trip trip2 = new Trip(new Date(400l), 300.0, new ArrayList<>());
        Trip trip3 = new Trip(new Date(300l), 400.0, new ArrayList<>());
        List<Trip> trips = new ArrayList<>();
        trips.add(trip1);
        trips.add(trip2);
        trips.add(trip3);
        Mockito.when(tripRepository.getTripUserJoined(Mockito.anyLong())).thenReturn(trips);
        //When
        List<Trip> result = tripService.getTripsUserJoined();
        //Then
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(200l, result.get(0).getDate().getTime());
        Assert.assertEquals(300.0, result.get(1).getDistance(), 0.1);
    }

    @Test
    public void getTripsByLocation() {
        //Given
        Mockito.when(googleMapService.getCoordinates(Mockito.any())).thenReturn(new Location(0.0, 1.1));
        Trip trip1 = new Trip(new Date(200l), 200.0, new ArrayList<>());
        Trip trip2 = new Trip(new Date(400l), 300.0, new ArrayList<>());
        Trip trip3 = new Trip(new Date(300l), 400.0, new ArrayList<>());
        List<Trip> trips = new ArrayList<>();
        trips.add(trip1);
        trips.add(trip2);
        trips.add(trip3);
        Mockito.when(tripRepository.getTripByLocationRadius(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyInt()))
                .thenReturn(trips);
        //When
        List<Trip> result = tripService.getTripsByLocation("aaa", 10);
        //Then
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(200l, result.get(0).getDate().getTime());
        Assert.assertEquals(300.0, result.get(1).getDistance(), 0.1);
    }

    @Test
    public void saveTrip() {
        //Given
        Trip trip1 = new Trip(new Date(200l), 200.0, new ArrayList<>());
        //When
        tripService.saveTrip(trip1);
        //Then
        Mockito.verify(tripRepository).save(trip1);
    }

    @Test
    public void deleteTrip() {
        //Given
        Trip trip1 = new Trip(new Date(200l), 200.0, new ArrayList<>());
        trip1.setCreator(user);
        Mockito.when(tripRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(trip1));
        Mockito.doNothing().when(tripRepository).delete(Mockito.any());
        //When
        tripService.deleteTrip(1l);
        //Then
        Mockito.verify(tripRepository).delete(trip1);
    }
}