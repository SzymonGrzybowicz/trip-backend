package com.kodilla.tripbackend.controller;

import com.kodilla.tripbackend.domains.UserDto;
import com.kodilla.tripbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerUser(@RequestBody UserDto userDto, HttpServletResponse response) {
           if (!userService.registerUser(userDto)){
               response.setStatus(HttpServletResponse.SC_CONFLICT);
           }
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(HttpServletRequest request, HttpServletResponse response) {
        if (!userService.deleteUser(request)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{newPassword}/{oldPassword}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changePassword(HttpServletResponse response, @PathVariable String newPassword, @PathVariable String oldPassword) {
        if (!userService.changePassword(newPassword, oldPassword)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/join/{tripId}", method = RequestMethod.PUT)
    public void joinUserToTrip(@PathVariable long tripId, HttpServletResponse response) {
        if (!userService.joinUserToTrip(tripId)){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/detach/{tripId}", method = RequestMethod.PUT)
    public void detachUserFromTrip(@PathVariable long tripId, HttpServletResponse response) {
        if (!userService.detachUserFromTrip(tripId)){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/buyTicket/{eventId}/{smsCode}", method = RequestMethod.PUT)
    public void buyTicket(@PathVariable long eventId, @PathVariable String smsCode, HttpServletResponse response) {
        if (!userService.buyTicket(eventId, smsCode)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}