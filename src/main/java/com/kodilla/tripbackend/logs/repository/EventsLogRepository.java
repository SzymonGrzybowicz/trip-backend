package com.kodilla.tripbackend.logs.repository;

import com.kodilla.tripbackend.logs.EventsLogEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsLogRepository extends CrudRepository<EventsLogEntity, Long> {
}
