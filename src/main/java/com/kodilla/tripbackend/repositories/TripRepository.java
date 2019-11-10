package com.kodilla.tripbackend.repositories;

import com.kodilla.tripbackend.domains.Trip;
import com.kodilla.tripbackend.domains.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TripRepository extends CrudRepository<Trip, Long> {

    @Override
    List<Trip> findAll();

    List<Trip> findByCreator(User creator);
}
