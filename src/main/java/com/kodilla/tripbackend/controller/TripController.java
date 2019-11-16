package com.kodilla.tripbackend.controller;

import com.kodilla.tripbackend.domains.TripDto;
import com.kodilla.tripbackend.mapper.TripMapper;
import com.kodilla.tripbackend.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/trip")
public class TripController {

    @Autowired
    private TripService service;

    @Autowired
    private TripMapper mapper;

    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TripDto> getAllTrips() {
        return mapper.mapToDtoList(service.getAllTrips());
    }

    @RequestMapping(value = "/createdByUser", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TripDto> getTripsCreatedByUser() {
        return mapper.mapToDtoList(service.getTripsCreatedByUser());
    }

    @RequestMapping(value = "/userJoined", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TripDto> getTripUserJoined() {
        return mapper.mapToDtoList(service.getTripsUserJoined());
    }

    @RequestMapping(value = "/{googleId}/{radiusInKM}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TripDto> getTripsByLocation(@PathVariable String googleId, @PathVariable int radiusInKM) {
        return mapper.mapToDtoList(service.getTripsByLocation(googleId, radiusInKM));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createTrip(@RequestBody TripDto tripDto, HttpServletResponse response) {
        if (!service.saveTrip(mapper.mapToTrip(tripDto))) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{tripId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteTrip(@PathVariable Long tripId, HttpServletResponse response) {
        if (!service.deleteTrip(tripId)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateTrip(@RequestBody TripDto tripDto, HttpServletResponse response) {
        if (!service.updateTrip(tripDto)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
