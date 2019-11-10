package com.kodilla.tripbackend.controller;

import com.kodilla.tripbackend.domains.TripDto;
import com.kodilla.tripbackend.mapper.TripMapper;
import com.kodilla.tripbackend.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/trip")
public class TripController {

    @Autowired
    TripService service;

    @Autowired
    TripMapper mapper;

    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TripDto> getAllTrips() {
        return mapper.mapToDtoList(service.getAllTrips());
    }

    @RequestMapping(value = "/createdByUser", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TripDto> getTripsCreatedByUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return mapper.mapToDtoList(service.getTripsCreatedByUser(username));
    }

    @RequestMapping(value = "/userJoined", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TripDto> getTripUserJoined() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return mapper.mapToDtoList(service.getTripsUserJoined(username));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createTrip(@RequestBody TripDto tripDto, HttpServletResponse response) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        if (!service.saveTrip(username, mapper.mapToTrip(tripDto))) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{tripId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteTrip(@PathVariable Long tripId) {
        service.deleteTrip(tripId);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateTrip(@RequestBody TripDto tripDto, HttpServletResponse response) {
        if (!service.updateTrip(tripDto)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


//    @RequestMapping(name = "/trip/{longitude}/{latitude}/{radius}", method = RequestMethod.GET)
//    public List<Trip> getTripsByLocation(@RequestParam double longitude, @RequestParam double latitude, @RequestParam int radius) {
//        // TODO
//        return new ArrayList<>();
//    }
}
