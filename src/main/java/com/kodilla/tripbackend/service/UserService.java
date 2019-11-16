package com.kodilla.tripbackend.service;

import com.kodilla.tripbackend.domains.Event;
import com.kodilla.tripbackend.domains.Trip;
import com.kodilla.tripbackend.domains.User;
import com.kodilla.tripbackend.domains.UserDto;
import com.kodilla.tripbackend.repositories.EventRepository;
import com.kodilla.tripbackend.repositories.TripRepository;
import com.kodilla.tripbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private EventRepository eventRepository;

    public boolean registerUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return false;
        }

        User user = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), true);
        userRepository.save(user);

        return true;
    }

    public boolean joinUserToTrip(long tripId) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        Optional<User> optionalUser = getCurrentUser();
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

    public boolean detachUserFromTrip(long tripId) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        Optional<User> optionalUser = getCurrentUser();
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

     Optional<User> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByUsername(username);
    }

    public boolean buyTicket(long eventId, String smsCode) {
        //here should be buying logic, but for this moment SMS code "1234" is correct
        if (!smsCode.equals("1234")){
            return false;
        }
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        Optional<User> optionalUser = getCurrentUser();
        if (!optionalEvent.isPresent() || !optionalUser.isPresent()) {
            return false;
        }
        Event event = optionalEvent.get();
        User user = optionalUser.get();
        event.getUsersSignedUp().add(user);
        user.getEvents().add(event);
        userRepository.save(user);
        return true;
    }
}
