package com.kodilla.tripbackend.repositories;

import com.kodilla.tripbackend.domains.Event;
import com.kodilla.tripbackend.domains.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    @Override
    List<Event> findAll();

    List<Event> findByCreator(User creator);

    List<Event> getEventByLocationRadius(
            @Param("LONGITUDE") double longitude, @Param("LATITUDE") double latitude, @Param("RADIUS") int radiusInKm);

    List<Event> getEventUserJoined(@Param("USER_ID") Long userId);
}
