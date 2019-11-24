package com.kodilla.tripbackend.logs;

import com.kodilla.tripbackend.logs.repository.GoogleLogsRepository;
import com.kodilla.tripbackend.service.UserService;
import lombok.Setter;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class Watcher {

    @Autowired
    @Setter
    private GoogleLogsRepository googleLogsRepository;

    @Autowired
    @Setter
    private UserService userService;

    @After("execution(* com.kodilla.tripbackend.service.GoogleMapService.getSuggestions())")
    public void saveGoogleSuggestionLog() throws Throwable {
        googleLogsRepository.save(
                new GoogleLogsEntity(
                        new Date(System.currentTimeMillis()),
                        userService.getCurrentUser().orElse(null)
                )
        );
    }
}
