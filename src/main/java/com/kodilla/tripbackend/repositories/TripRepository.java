package com.kodilla.tripbackend.repositories;

import com.kodilla.tripbackend.domains.Trip;
import com.kodilla.tripbackend.domains.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TripRepository extends CrudRepository<Trip, Long> {

    @Override
    List<Trip> findAll();

    List<Trip> findByCreator(User creator);

    List<Trip> getTripByLocationRadius(
            @Param("LONGITUDE") double longitude, @Param("LATITUDE") double latitude, @Param("RADIUS") int radiusInKm);

    List<Trip> getTripUserJoined(@Param("USER_ID") Long userId);
}
