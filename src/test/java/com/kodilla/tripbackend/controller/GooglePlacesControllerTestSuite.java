package com.kodilla.tripbackend.controller;

import com.kodilla.tripbackend.service.GoogleMapService;
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
public class GooglePlacesControllerTestSuite {

    private static final String CONTROLLER_ADDRESS = "/google";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @MockBean
    GoogleMapService googleMapService;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(value = "spring")
    @Test
    public void getSuggestions() throws Exception {
        //Given
        Mockito.when(googleMapService.getSuggestions(Mockito.anyString())).thenReturn(new ArrayList<>());

        //When
        mvc.perform(get(CONTROLLER_ADDRESS + "/suggestion/war").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
        Mockito.verify(googleMapService).getSuggestions("war");
    }

    @WithMockUser(value = "spring")
    @Test
    public void getDistance() throws Exception {
        //Given
        Mockito.when(googleMapService.getDistance(Mockito.anyString(), Mockito.anyString())).thenReturn("");

        //When
        mvc.perform(get(CONTROLLER_ADDRESS + "/distance/aaa/bbb").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //Then
        Mockito.verify(googleMapService).getDistance("aaa", "bbb");
    }
}