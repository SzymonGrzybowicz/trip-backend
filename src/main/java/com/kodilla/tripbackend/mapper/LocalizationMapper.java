package com.kodilla.tripbackend.mapper;

import com.kodilla.tripbackend.domains.Localization;
import com.kodilla.tripbackend.domains.LocalizationDTO;
import com.kodilla.tripbackend.google_maps_json.geolocation.Geometry;
import com.kodilla.tripbackend.google_maps_json.geolocation.Location;
import com.kodilla.tripbackend.service.GoogleMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocalizationMapper {

    @Autowired
    GoogleMapService googleMapService;

    public List<LocalizationDTO> mapToDtoList(List<Localization> localizations) {
        return localizations.stream()
                .map(l -> new LocalizationDTO(l.getGoogleId(), l.getMainDescription(), l.getSecondaryDescription(), l.getNumberInTrip()))
                .collect(Collectors.toList());
    }

    public List<Localization> mapToLocalisationList(List<LocalizationDTO> dtos) {
        List<Localization> result = new ArrayList<>();
        for (LocalizationDTO dto : dtos) {
            Location location = googleMapService.getCoordinates(dto.getGoogleId());
            result.add(new Localization(
                    dto.getMainDescription(),
                    dto.getSecondaryDescription(),
                    location.getLongitude(),
                    location.getLatitude(),
                    dto.getNumberInTrip(),
                    dto.getGoogleId()));

        }
        return result;
    }
}
