package com.kodilla.tripbackend.logs;

import com.kodilla.tripbackend.logs.repository.GoogleLogsRepository;
import com.kodilla.tripbackend.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class WatcherTestTestSuite {

    @Mock
    private GoogleLogsRepository googleLogsRepository;

    @Mock
    private UserService userService;

    @Test
    public void saveLogsGoogleSuggestionTest() throws Throwable {
        //Given
        Watcher watcher = new Watcher();
        watcher.setGoogleLogsRepository(googleLogsRepository);
        watcher.setUserService(userService);
        Mockito.when(userService.getCurrentUser()).thenReturn(Optional.ofNullable(null));
        //When
        watcher.saveGoogleSuggestionLog();
        //Then
        Mockito.verify(googleLogsRepository).save(Mockito.any());
    }
}