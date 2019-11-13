package com.kodilla.tripbackend.mapper;

import com.kodilla.tripbackend.domains.Localization;
import com.kodilla.tripbackend.domains.LocalizationDto;
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
    private GoogleMapService googleMapService;

    public List<LocalizationDto> mapToDtoList(List<Localization> localizations) {
        return localizations.stream()
                .map(l -> new LocalizationDto(l.getGoogleId(), l.getMainDescription(), l.getSecondaryDescription(), l.getNumberInTrip()))
                .collect(Collectors.toList());
    }

    public LocalizationDto mapToDto(Localization localization) {
        return new LocalizationDto(localization.getGoogleId(),
                localization.getMainDescription(),
                localization.getSecondaryDescription(), localization.getNumberInTrip());
    }

    public List<Localization> mapToLocalisationList(List<LocalizationDto> dtos) {
        List<Localization> result = new ArrayList<>();
        for (LocalizationDto dto : dtos) {
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

    public Localization mapToLocalization(LocalizationDto localizationDto) {
        Location location = googleMapService.getCoordinates(localizationDto.getGoogleId());
        return new Localization(
                localizationDto.getMainDescription(), localizationDto.getSecondaryDescription(),
                location.getLongitude(), location.getLatitude(), localizationDto.getNumberInTrip(),
                localizationDto.getGoogleId()
        );
    }
}
