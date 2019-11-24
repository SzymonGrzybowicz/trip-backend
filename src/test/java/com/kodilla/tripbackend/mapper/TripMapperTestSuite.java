package com.kodilla.tripbackend.mapper;

import com.kodilla.tripbackend.domains.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TripMapperTestSuite {

    @Mock
    LocalizationMapper localizationMapper;

    @Test
    public void mapToDtoList() {
        //Given
        List<Trip> trips = new ArrayList<>();
        trips.add(new Trip(0l, new ArrayList<>(), new Date(200l), 100, new ArrayList<>(), null, null));
        TripMapper tripMapper = new TripMapper();
        Mockito.when(localizationMapper.mapToDtoList(Mockito.anyList())).thenReturn(new ArrayList<>());
        tripMapper.setLocalizationMapper(localizationMapper);
        //When
        List<TripDto> result = tripMapper.mapToDtoList(trips);
        //Then
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(200, result.get(0).getDate().getTime());
        Assert.assertEquals(100, result.get(0).getDistance(), 0.1);
    }

    @Test
    public void mapToTrip() {
        //Given
        TripDto tripDto = new TripDto(0l, new ArrayList<LocalizationDto>(), new Date(200l), 100, 1);
        TripMapper tripMapper = new TripMapper();
        tripMapper.setLocalizationMapper(localizationMapper);
        Mockito.when(localizationMapper.mapToLocalisationList(Mockito.anyList())).thenReturn(new ArrayList<>());
        //When
        Trip result = tripMapper.mapToTrip(tripDto);
        //Then
        Assert.assertEquals(200l, result.getDate().getTime());
        Assert.assertEquals(100, result.getDistance(), 0.1);
    }
}