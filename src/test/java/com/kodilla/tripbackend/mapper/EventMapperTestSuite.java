package com.kodilla.tripbackend.mapper;

import com.kodilla.tripbackend.domains.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class EventMapperTestSuite {

    @Mock
    private LocalizationMapper localizationMapper;

    @Test
    public void mapToEvent() {
        //Given
        EventDto eventDto = new EventDto(
                0l,
                new LocalizationDto("test", "test", "test", 0),
                new Date(100l),
                0,
                10);
        Localization localization = new Localization();
        Mockito.when(localizationMapper.mapToLocalization(Mockito.any())).thenReturn(localization);
        EventMapper eventMapper = new EventMapper();
        eventMapper.setLocalizationMapper(localizationMapper);
        //When
        Event result = eventMapper.mapToEvent(eventDto);
        //Then
        Assert.assertEquals(10.0, result.getPrice(), 0.1);
        Assert.assertEquals(100l, result.getDate().getTime());
    }

    @Test
    public void mapToDtoList() {
        //Given
        EventMapper eventMapper = new EventMapper();
        eventMapper.setLocalizationMapper(localizationMapper);
        Mockito.when(localizationMapper.mapToDto(Mockito.any())).thenReturn(new LocalizationDto("", "", "", 0));
        List<Event> events = new ArrayList<>();
        events.add(new Event(0l, null, new Date(200l), 20.0, new ArrayList<>(), null, null));
        events.add(new Event(2l, null, new Date(300l), 30.0, new ArrayList<>(), null, null));
        //Given
        List<EventDto> result = eventMapper.mapToDtoList(events);
        //Then
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(200l, result.get(0).getDate().getTime());
        Assert.assertEquals(Long.valueOf(2), result.get(1).getId());
    }
}