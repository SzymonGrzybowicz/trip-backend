package com.kodilla.tripbackend.controller;

import com.kodilla.tripbackend.service.EventService;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventControllerTestSuite {

    private static final String CONTROLLER_ADDRESS = "/event";

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
    EventService eventService;

    @WithMockUser(value = "spring")
    @Test
    public void getAllEvents() throws Exception {
        //Given
        Mockito.when(eventService.getAllEvents()).thenReturn(new ArrayList<>());
        //When
        mvc.perform(get(CONTROLLER_ADDRESS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //Then
        Mockito.verify(eventService).getAllEvents();
    }

    @WithMockUser(value = "spring")
    @Test
    public void getEventsCreatedByUser() throws Exception {
        //Given
        Mockito.when(eventService.getEventsCreatedByUser()).thenReturn(new ArrayList<>());
        //When
        mvc.perform(get(CONTROLLER_ADDRESS + "/createdByUser").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
        Mockito.verify(eventService).getEventsCreatedByUser();
    }

    @WithMockUser(value = "spring")
    @Test
    public void getEventUserJoined() throws Exception {
        //Given
        Mockito.when(eventService.getEventsUserJoined()).thenReturn(new ArrayList<>());
        //When
        mvc.perform(get(CONTROLLER_ADDRESS + "/userJoined").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
        Mockito.verify(eventService).getEventsUserJoined();
    }

    @WithMockUser(value = "spring")
    @Test
    public void getEventsByLocation() throws Exception {
        //Given
        Mockito.when(eventService.getEventsByLocation("aaa", 100)).thenReturn(new ArrayList<>());
        //When
        mvc.perform(get(CONTROLLER_ADDRESS + "/aaa/100").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
        Mockito.verify(eventService).getEventsByLocation("aaa", 100);
    }

    @WithMockUser(value = "spring")
    @Test
    public void createEvent() throws Exception {
//        //Given
//        EventDto eventDto = new EventDto(1l, new LocalizationDto("a", "a", "a", -1),
//                new Date(200l), 10, 10);
//        Gson gson = new Gson();
//        String jsonContent = gson.toJson(eventDto);
////        Mockito.when(eventService.saveEvent(Mockito.any())).thenReturn(true);
//        //When
//        mvc.perform(
//                post(CONTROLLER_ADDRESS)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonContent)
//        );
//                .andExpect(status().isOk());
//        //Then
//        Mockito.verify(eventService).saveEvent(Mockito.any());  todo
    }

    @WithMockUser(value = "spring")
    @Test
    public void deleteEvent() throws Exception {
        //Given
        Mockito.when(eventService.deleteEvent(Mockito.anyLong())).thenReturn(true);
        //When
        mvc.perform(delete(CONTROLLER_ADDRESS + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
        Mockito.verify(eventService).deleteEvent(1l);
    }

    @WithMockUser(value = "spring")
    @Test
    public void updateEvent() {
        //todo
    }
}