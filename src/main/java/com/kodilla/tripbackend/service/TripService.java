package com.kodilla.tripbackend.service;

import com.kodilla.tripbackend.domains.Localization;
import com.kodilla.tripbackend.domains.Trip;
import com.kodilla.tripbackend.domains.TripDto;
import com.kodilla.tripbackend.domains.User;
import com.kodilla.tripbackend.mapper.LocalizationMapper;
import com.kodilla.tripbackend.repositories.LocalizationRepository;
import com.kodilla.tripbackend.repositories.TripRepository;
import com.kodilla.tripbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    @Autowired
    TripRepository tripRepository;

    @Autowired
    LocalizationMapper localizationMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LocalizationRepository localizationRepository;

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public boolean saveTrip(String username, Trip trip) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getCreatedTrips().add(trip);
            trip.setCreator(user);
            for (Localization localization: trip.getLocalizations()) {
                localization.setTrip(trip);
            }
            tripRepository.save(trip);
            return true;
        }
        return false;
    }

    public boolean deleteTrip(Long id) {
        Optional<Trip> optionalTrip = tripRepository.findById(id);
        if (optionalTrip.isPresent()){
            Trip trip = optionalTrip.get();
            for (Localization localization: trip.getLocalizations()){
                localizationRepository.delete(localization);
            }
            tripRepository.delete(trip);
        }
        return false;
    }

    public boolean updateTrip(TripDto tripDto) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripDto.getId());
        if (optionalTrip.isPresent()) return false;

        Trip trip = optionalTrip.get();
        trip.setLocalizations(localizationMapper.mapToLocalisationList(tripDto.getLocalizations()));
        trip.setDate(tripDto.getDate());
        trip.setDistance(tripDto.getDistance());
        tripRepository.save(trip);
        return true;
    }
}
