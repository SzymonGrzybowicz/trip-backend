package com.kodilla.tripbackend.controller;

import com.google.gson.Gson;
import com.kodilla.tripbackend.domains.UserDto;
import com.kodilla.tripbackend.service.UserService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTestSuite {

    private static final String CONTROLLER_ADDRESS = "/user";

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
    UserService userService;

    @Test
    public void registerUser() throws Exception {
        //Given
        Mockito.when(userService.registerUser(Mockito.any())).thenReturn(true);
        UserDto userDto = new UserDto("aaa", "bbb");
        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);
        //When
        mvc.perform(
                post(CONTROLLER_ADDRESS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .characterEncoding("UTF-8")
        ).andExpect(status().isOk());

        //Then
        Mockito.verify(userService).registerUser(Mockito.any());
    }

    @WithMockUser(value = "spring")
    @Test
    public void deleteUser() throws Exception {
        //Given
        Mockito.when(userService.deleteUser()).thenReturn(true);

        //When
        mvc.perform(delete(CONTROLLER_ADDRESS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
        Mockito.verify(userService).deleteUser();
    }

    @WithMockUser(value = "spring")
    @Test
    public void changePassword() throws Exception {
        //Given
        Mockito.when(userService.changePassword(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        //When
        mvc.perform(put(CONTROLLER_ADDRESS + "/aaa/bbb").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
        Mockito.verify(userService).changePassword("aaa", "bbb");
    }

    @WithMockUser(value = "spring")
    @Test
    public void joinUserToTrip() throws Exception {
        //Given
        Mockito.when(userService.joinUserToTrip(Mockito.anyLong())).thenReturn(true);
        //When
        mvc.perform(put(CONTROLLER_ADDRESS + "/join/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
        Mockito.verify(userService).joinUserToTrip(1);
    }

    @WithMockUser(value = "spring")
    @Test
    public void detachUserFromTrip() throws Exception {
        //Given
        Mockito.when(userService.detachUserFromTrip(Mockito.anyLong())).thenReturn(true);
        //When
        mvc.perform(put(CONTROLLER_ADDRESS + "/detach/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
        Mockito.verify(userService).detachUserFromTrip(1);
    }

    @WithMockUser(value = "spring")
    @Test
    public void buyTicket() throws Exception {
        //Given
        Mockito.when(userService.buyTicket(Mockito.anyLong(), Mockito.anyString())).thenReturn(true);
        //When
        mvc.perform(put(CONTROLLER_ADDRESS + "/buyTicket/1/1234").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //Then
        Mockito.verify(userService).buyTicket(1l, "1234");
    }
}