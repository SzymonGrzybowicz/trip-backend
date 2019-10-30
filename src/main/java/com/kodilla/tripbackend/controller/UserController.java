package com.kodilla.tripbackend.controller;

import com.kodilla.tripbackend.domains.UserDto;
import com.kodilla.tripbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerUser(@RequestBody UserDto userDto, HttpServletResponse response) {
           if (!userService.registerUser(userDto)){
               response.setStatus(HttpServletResponse.SC_CONFLICT);
           }
    }

    @RequestMapping(value = "/join/{userId}/{tripId}", method = RequestMethod.PATCH)
    public void joinUserToTrip(@PathVariable long userId, @PathVariable long tripId, HttpServletResponse response) {
        if (!userService.joinUserToTrip(userId, tripId)){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/detach/{userId}/{tripId}", method = RequestMethod.PATCH)
    public void detachUserFromTrip(@PathVariable long userId, @PathVariable long tripId, HttpServletResponse response) {
        if (!userService.detachUserFromTrip(userId, tripId)){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}