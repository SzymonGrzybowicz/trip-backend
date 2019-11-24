package com.kodilla.tripbackend.controller;

import com.kodilla.tripbackend.service.TripService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TripControllerTestSuite {

    private static final String CONTROLLER_ADDRESS = "/trip";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @MockBean
    TripService tripService;

    @WithMockUser(value = "spring")
    @Test
    public void getAllTrips() throws Exception {
        //Given
        Mockito.when(tripService.getAllTrips()).thenReturn(new ArrayList<>());
        //When
        mvc.perform(get(CONTROLLER_ADDRESS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
        Mockito.verify(tripService).getAllTrips();
    }


    @WithMockUser(value = "spring")
    @Test
    public void getTripsCreatedByUser() throws Exception {
        //Given
        Mockito.when(tripService.getTripsCreatedByUser()).thenReturn(new ArrayList<>());
        //When
        mvc.perform(get(CONTROLLER_ADDRESS + "/createdByUser").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
        Mockito.verify(tripService).getTripsCreatedByUser();
    }

    @WithMockUser(value = "spring")
    @Test
    public void getTripUserJoined() throws Exception {
        //Given
        Mockito.when(tripService.getTripsUserJoined()).thenReturn(new ArrayList<>());
        //When
        mvc.perform(get(CONTROLLER_ADDRESS + "/userJoined").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
        Mockito.verify(tripService).getTripsUserJoined();
    }

    @WithMockUser(value = "spring")
    @Test
    public void getTripsByLocation() throws Exception {
        //Given
        Mockito.when(tripService.getTripsByLocation(Mockito.anyString(), Mockito.anyInt())).thenReturn(new ArrayList<>());
        //When
        mvc.perform(get(CONTROLLER_ADDRESS + "/aaa/10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
        Mockito.verify(tripService).getTripsByLocation("aaa", 10);
    }

    @WithMockUser(value = "spring")
    @Test
    public void createTrip() {
        //todo
    }

    @WithMockUser(value = "spring")
    @Test
    public void deleteTrip() throws Exception {
        //Given
        Mockito.when(tripService.deleteTrip(Mockito.anyLong())).thenReturn(true);
        //When
        mvc.perform(delete(CONTROLLER_ADDRESS + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
        Mockito.verify(tripService).deleteTrip(1l);
    }

    @WithMockUser(value = "spring")
    @Test
    public void updateTrip() {
        //todo
    }
}