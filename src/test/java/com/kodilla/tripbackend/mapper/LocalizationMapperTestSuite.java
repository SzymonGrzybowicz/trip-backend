package com.kodilla.tripbackend.mapper;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kodilla.tripbackend.domains.Localization;
import com.kodilla.tripbackend.domains.LocalizationDto;
import com.kodilla.tripbackend.google_maps_json.geolocation.Location;
import com.kodilla.tripbackend.service.GoogleMapService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class LocalizationMapperTestSuite {

    @Mock
    private GoogleMapService googleMapService;

    @Test
    public void mapToDtoList() {
        //Given
        List<Localization> localizations = new ArrayList<>();
        localizations.add(new Localization("test1", "test1",
                1.1, 2.2, 0, "aaa"));
        localizations.add(new Localization("test2", "test2",
                3.3, 4.4, 0, "bbb"));
        LocalizationMapper localizationMapper = new LocalizationMapper();
        //When
        List<LocalizationDto> result = localizationMapper.mapToDtoList(localizations);
        //Then
        Assert.assertEquals(2, result.size());
        Assert.assertEquals("test1", result.get(0).getMainDescription());
        Assert.assertEquals("bbb", result.get(1).getGoogleId());
    }

    @Test
    public void mapToDto() {
        //Given
        Localization localization = new Localization("test2", "test2",
                3.3, 4.4, 0, "bbb");
        LocalizationMapper localizationMapper = new LocalizationMapper();
        //When
        LocalizationDto result = localizationMapper.mapToDto(localization);
        //Then
        Assert.assertEquals("test2", result.getMainDescription());
        Assert.assertEquals(0, result.getNumberInTrip());
        Assert.assertEquals("bbb", result.getGoogleId());
    }

    @Test
    public void mapToLocalisationList() {
        //Given
        List<LocalizationDto> localizationsDtos = new ArrayList<>();
        localizationsDtos.add(new LocalizationDto("aaa", "test1", "test2", 0));
        localizationsDtos.add(new LocalizationDto("bbb", "test3", "test4", 1));
        LocalizationMapper localizationMapper = new LocalizationMapper();
        localizationMapper.setGoogleMapService(googleMapService);
        Mockito.when(googleMapService.getCoordinates(Mockito.any())).thenReturn(new Location());
        //When
        List<Localization> result = localizationMapper.mapToLocalisationList(localizationsDtos);
        //Then
        Assert.assertEquals(2, result.size());
        Assert.assertEquals("aaa", result.get(0).getGoogleId());
        Assert.assertEquals("test3", result.get(1).getMainDescription());
    }

    @Test
    public void mapToLocalization() {
        //Given
        LocalizationDto localizationDto = new LocalizationDto("aaa", "test1",
                "test2", 1);
        LocalizationMapper localizationMapper = new LocalizationMapper();
        localizationMapper.setGoogleMapService(googleMapService);
        Mockito.when(googleMapService.getCoordinates(Mockito.any())).thenReturn(new Location());
        //When
        Localization result = localizationMapper.mapToLocalization(localizationDto);
        //Then
        Assert.assertEquals("test2", result.getSecondaryDescription());
        Assert.assertEquals("aaa", result.getGoogleId());
        Assert.assertEquals("test1", result.getMainDescription());
    }
}