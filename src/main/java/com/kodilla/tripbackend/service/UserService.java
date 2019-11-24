package com.kodilla.tripbackend.service;

import com.kodilla.tripbackend.domains.Event;
import com.kodilla.tripbackend.domains.Trip;
import com.kodilla.tripbackend.domains.User;
import com.kodilla.tripbackend.domains.UserDto;
import com.kodilla.tripbackend.repositories.EventRepository;
import com.kodilla.tripbackend.repositories.TripRepository;
import com.kodilla.tripbackend.repositories.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    @Setter
    private UserRepository userRepository;

    @Autowired
    @Setter
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Setter
    private TripRepository tripRepository;

    @Autowired
    @Setter
    private EventRepository eventRepository;

    public boolean registerUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return false;
        }

        User user = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), true);
        userRepository.save(user);

        return true;
    }

    public boolean deleteUser(HttpServletRequest request) {
        Optional<User> optionalUser = getCurrentUser();
        if (!optionalUser.isPresent()) {
            return false;
        }
        User user = optionalUser.get();
        userRepository.delete(user);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, null, auth);
        }
        return true;
    }

    public boolean
    joinUserToTrip(long tripId) {
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

     public Optional<User> getCurrentUser() {
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
        if (event.getCreator().equals(user)) {
            return false;
        }
        event.getUsersSignedUp().add(user);
        user.getEvents().add(event);
        userRepository.save(user);
        return true;
    }

    public boolean changePassword(String newPassword, String oldPassword) {
        Optional<User> optionalUser = getCurrentUser();
        if (!optionalUser.isPresent()) {
            return false;
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }
        user.setPassword(newPassword);
        userRepository.save(user);
        return true;
    }
}
