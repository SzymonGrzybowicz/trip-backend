package com.kodilla.tripbackend.logs.repository;

import com.kodilla.tripbackend.logs.TripsLogsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripsLogsRepository extends CrudRepository<TripsLogsEntity, Long> {
}
