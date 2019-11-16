package com.kodilla.tripbackend.logs;

import com.kodilla.tripbackend.logs.repository.EventsLogRepository;
import com.kodilla.tripbackend.logs.repository.TripsLogsRepository;
import com.kodilla.tripbackend.repositories.EventRepository;
import com.kodilla.tripbackend.repositories.TripRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class Watcher {

    @Autowired
    EventsLogRepository eventsLogRepository;

    @Autowired
    TripsLogsRepository tripsLogsRepository;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    EventRepository eventRepository;

    @Around("execution(* com.kodilla.tripbackend.controller.TripController.getAllTrips())")
    public void measureGetAllTripsTime(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            long begin = System.currentTimeMillis();
            proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();
            tripsLogsRepository.save(
                    new TripsLogsEntity(
                            new Date(System.currentTimeMillis()),
                            tripRepository.count(),
                            begin - end)
            );
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        }
    }

    @Around("execution(* com.kodilla.tripbackend.controller.EventController.getAllEvents())")
    public void measureGetAllEventsTime(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            long begin = System.currentTimeMillis();
            proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();
            eventsLogRepository.save(
                    new EventsLogEntity(
                            new Date(System.currentTimeMillis()),
                            eventRepository.count(),
                            end-begin
                    ));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw  throwable;
        }
    }
}
