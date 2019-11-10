package com.kodilla.tripbackend.controller;

import com.kodilla.tripbackend.domains.LocalizationDTO;
import com.kodilla.tripbackend.service.GoogleMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/google")
public class GooglePlacesController {

    @Autowired
    GoogleMapService service;

    @RequestMapping(value = "/suggestion/{input}", method = RequestMethod.GET)
    public List<LocalizationDTO> getSuggestions(@PathVariable String input) {
        return service.getSuggestions(input);
    }

    @RequestMapping(value = "/distance/{firstPlaceId}/{secondPlaceId}", method = RequestMethod.GET)
    public String getDistance(@PathVariable String firstPlaceId, @PathVariable String secondPlaceId) {
        return service.getDistance(firstPlaceId, secondPlaceId);
    }

}
