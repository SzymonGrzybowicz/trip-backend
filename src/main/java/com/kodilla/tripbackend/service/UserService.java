package com.kodilla.tripbackend.service;

import com.kodilla.tripbackend.domains.Trip;
import com.kodilla.tripbackend.domains.User;
import com.kodilla.tripbackend.domains.UserDto;
import com.kodilla.tripbackend.repositories.TripRepository;
import com.kodilla.tripbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TripRepository tripRepository;

    public boolean registerUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return false;
        }

        User user = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), true);
        userRepository.save(user);

        return true;
    }

    public boolean joinUserToTrip(String username, long tripId) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalTrip.isPresent() && optionalUser.isPresent()){
            Trip trip = optionalTrip.get();
            User user = optionalUser.get();
            if (user.getTrips().contains(trip)){
                return false;
            } if (trip.getCreator().equals(user)) {
                return false;
            }
            user.getTrips().add(trip);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean detachUserFromTrip(String username, long tripId) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalTrip.isPresent() && optionalUser.isPresent()){
            Trip trip = optionalTrip.get();
            User user = optionalUser.get();
            if (user.getTrips().contains(trip)){
                user.getTrips().remove(trip);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}
