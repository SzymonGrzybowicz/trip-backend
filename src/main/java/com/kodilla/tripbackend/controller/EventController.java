package com.kodilla.tripbackend.controller;

import com.kodilla.tripbackend.domains.EventDto;
import com.kodilla.tripbackend.mapper.EventMapper;
import com.kodilla.tripbackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin("*" )
@RequestMapping("/event" )
public class EventController {

    @Autowired
    EventService service;

    @Autowired
    EventMapper mapper;

    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<EventDto> getAllEvents() {
        return mapper.mapToDtoList(service.getAllEvents());
    }

    @RequestMapping(value = "/createdByUser", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<EventDto> getEventsCreatedByUser() {
        return mapper.mapToDtoList(service.getEventsCreatedByUser());
    }

    @RequestMapping(value = "/userJoined", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<EventDto> getEventUserJoined() {
        return mapper.mapToDtoList(service.getEventsUserJoined());
    }

    @RequestMapping(value = "/{googleId}/{radiusInKM}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<EventDto> getEventsByLocation(@PathVariable String googleId, @PathVariable int radiusInKM) {
        return mapper.mapToDtoList(service.getEventsByLocation(googleId, radiusInKM));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createEvent(@RequestBody EventDto eventDto, HttpServletResponse response) {
        if (!service.saveEvent(mapper.mapToEvent(eventDto))) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteEvent(@PathVariable Long eventId, HttpServletResponse response) {
       if (!service.deleteEvent(eventId)){
           response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
       }
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateEvent(@RequestBody EventDto eventDto, HttpServletResponse response) {
        if (!service.updateEvent(eventDto)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
