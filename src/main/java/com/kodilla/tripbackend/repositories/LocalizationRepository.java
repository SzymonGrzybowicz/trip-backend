package com.kodilla.tripbackend.repositories;

import com.kodilla.tripbackend.domains.Localization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizationRepository extends CrudRepository<Localization, Long> {
}
