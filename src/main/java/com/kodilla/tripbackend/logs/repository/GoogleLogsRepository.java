package com.kodilla.tripbackend.logs.repository;

import com.kodilla.tripbackend.logs.GoogleLogsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoogleLogsRepository extends CrudRepository<GoogleLogsEntity, Long> {
}
