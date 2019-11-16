package com.kodilla.tripbackend.scheduled;

import com.kodilla.tripbackend.domains.Event;
import com.kodilla.tripbackend.domains.Trip;
import com.kodilla.tripbackend.repositories.EventRepository;
import com.kodilla.tripbackend.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class DeleteScheduler {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private EventRepository eventRepository;

    @Scheduled(cron = "0 0 03 * * *")
    public void deleteOldEventsAndTrips() {
        deleteOldTrips();
        deleteOldEvents();
    }

    private void deleteOldEvents() {
        List<Event> events = eventRepository.findAll();
        for (Event event : events) {
            if (System.currentTimeMillis() - event.getDate().getTime() > TimeUnit.DAYS.toMillis(1)) {
                eventRepository.delete(event);
            }
        }
    }

    private void deleteOldTrips() {
        List<Trip> trips = tripRepository.findAll();
        for (Trip trip : trips) {
            if (System.currentTimeMillis() - trip.getDate().getTime() > TimeUnit.DAYS.toMillis(1)) {
                tripRepository.delete(trip);
            }
        }
    }
}
