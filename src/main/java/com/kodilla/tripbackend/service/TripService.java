package com.kodilla.tripbackend.service;

import com.kodilla.tripbackend.domains.*;
import com.kodilla.tripbackend.google_maps_json.geolocation.Location;
import com.kodilla.tripbackend.mapper.LocalizationMapper;
import com.kodilla.tripbackend.repositories.LocalizationRepository;
import com.kodilla.tripbackend.repositories.TripRepository;
import com.kodilla.tripbackend.repositories.WeatherForecastRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TripService {

    @Autowired
    @Setter
    private TripRepository tripRepository;

    @Autowired
    @Setter
    private LocalizationMapper localizationMapper;

    @Autowired
    @Setter
    private LocalizationRepository localizationRepository;

    @Autowired
    @Setter
    private WeatherService weatherService;

    @Autowired
    @Setter
    private WeatherForecastRepository weatherRepository;

    @Autowired
    @Setter
    private GoogleMapService googleMapService;

    @Autowired
    @Setter
    private UserService userService;

    public List<Trip> getAllTrips() {
        return tripRepository.findAll().stream()
                .map(t -> checkWeatherIsActualAndUpdate(t))
                .collect(Collectors.toList());
    }

    public List<Trip> getTripsCreatedByUser() {
        Optional<User> optionalUser = userService.getCurrentUser();
        if (!optionalUser.isPresent()) {
            return new ArrayList<>();
        }
        return tripRepository.findByCreator(optionalUser.get());
    }

    public List<Trip> getTripsUserJoined() {
        Optional<User> optionalUser = userService.getCurrentUser();
        if (!optionalUser.isPresent()) {
            return new ArrayList<>();
        }
        return tripRepository.getTripUserJoined(optionalUser.get().getId());
    }

    public List<Trip> getTripsByLocation(String googleId, int radius) {
        Location location = googleMapService.getCoordinates(googleId);
        return tripRepository.getTripByLocationRadius(
                location.getLongitude(),
                location.getLatitude(),
                radius
        );
    }

    public boolean saveTrip(Trip trip) {
        Optional<User> optionalUser = userService.getCurrentUser();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getCreatedTrips().add(trip);
            trip.setCreator(user);
            for (Localization localization : trip.getLocalizations()) {
                localization.setTrip(trip);
            }
            tripRepository.save(trip);
            return true;
        }
        return false;
    }

    public boolean deleteTrip(Long id) {
        Optional<Trip> optionalTrip = tripRepository.findById(id);
        Optional<User> optionalUser = userService.getCurrentUser();
        if (!optionalTrip.isPresent() || !optionalUser.isPresent()) {
            return false;
        }
        Trip trip = optionalTrip.get();
        User user = optionalUser.get();
        if (!trip.getCreator().equals(user)) {
            return false;
        }
        for (Localization localization : trip.getLocalizations()) {
            localizationRepository.delete(localization);
        }
        tripRepository.delete(trip);
        return true;
    }

    public boolean updateTrip(TripDto tripDto) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripDto.getId());
        if (!optionalTrip.isPresent()) return false;

        Trip trip = optionalTrip.get();
        trip.getLocalizations().forEach(
                l -> {
                    localizationRepository.delete(l);
                }
        );
        trip.setLocalizations(localizationMapper.mapToLocalisationList(tripDto.getLocalizations()));
        trip.getLocalizations().forEach(
                l-> {
                    l.setTrip(trip);
                }
        );
        if (trip.getWeatherForecast() != null) {
            weatherRepository.delete(trip.getWeatherForecast());
        }
        trip.setWeatherForecast(null);
        trip.setDate(tripDto.getDate());
        trip.setDistance(tripDto.getDistance());
        tripRepository.save(trip);
        return true;
    }

    private Trip checkWeatherIsActualAndUpdate(Trip trip) {
        final WeatherForecast[] forecast = {trip.getWeatherForecast()};
        if (trip.getDate().getTime() < System.currentTimeMillis() || trip.getDate().getTime() - System.currentTimeMillis() > TimeUnit.DAYS.toMillis(7)) {
            return trip;
        }

        final WeatherForecast[] resultForecast = new WeatherForecast[1];
        if (forecast[0] == null || System.currentTimeMillis() - forecast[0].getLastCheckDate().getTime() > TimeUnit.DAYS.toDays(1)) {
            if (forecast[0] != null) weatherRepository.delete(forecast[0]);
            for (Localization localization : trip.getLocalizations()) {
                if (localization.getNumberInTrip() == 0) {
                    resultForecast[0] = weatherService.getForecastForDate(
                            trip.getDate(),
                            localization.getLatitude(),
                            localization.getLongitude());
                }
            }
        }
        trip.setWeatherForecast(resultForecast[0]);
        tripRepository.save(trip);
        return trip;
    }
}
