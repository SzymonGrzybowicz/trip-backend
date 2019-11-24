package com.kodilla.tripbackend.service;

import com.kodilla.tripbackend.domains.User;
import com.kodilla.tripbackend.domains.UserDto;
import com.kodilla.tripbackend.repositories.EventRepository;
import com.kodilla.tripbackend.repositories.TripRepository;
import com.kodilla.tripbackend.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)

public class UserServiceTestSuite {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private EventRepository eventRepository;

    private UserService userService;

    @Before
    public void init(){
        userService = new UserService();
        userService.setUserRepository(userRepository);
        userService.setPasswordEncoder(passwordEncoder);
        userService.setTripRepository(tripRepository);
        userService.setEventRepository(eventRepository);
    }

    @Test
    public void registerUser() {
        //Given
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(new User());
        UserDto userDto = new UserDto("aaa", "bbb");
        //When
        userService.registerUser(userDto);
        //Then
        Mockito.verify(userRepository).save(Mockito.any());
    }

    @Test
//    @PrepareForTest(SecurityContextHolder.class)
    public void deleteUser() {
//        //Given
//        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(new User()));
//        Mockito.doNothing().when(userRepository).delete(Mockito.any());
//        PowerMockito.mockStatic(SecurityContextHolder.class);
//        PowerMockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("aaa");
//        //When
//        userService.deleteUser();
//        //Then
//        Mockito.verify(userRepository).delete(Mockito.any()); //todo
    }

    @Test
    public void joinUserToTrip() {
        //todo
    }

    @Test
    public void detachUserFromTrip() {

        //todo
    }

    @Test
    public void getCurrentUser() {
        //todo
    }

    @Test
    public void buyTicket() {
        //todo
    }

    @Test
    public void changePassword() {
        //todo
    }
}