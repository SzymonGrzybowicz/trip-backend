package com.kodilla.tripbackend.service;

import com.kodilla.tripbackend.AdminConfig;
import com.kodilla.tripbackend.dark_sky_weather_json.Daily;
import com.kodilla.tripbackend.dark_sky_weather_json.Forecast;
import com.kodilla.tripbackend.dark_sky_weather_json.Response;
import com.kodilla.tripbackend.domains.WeatherForecast;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTestSuite {

    @Mock
    RestTemplate restTemplate;

    @Mock
    AdminConfig adminConfig;

    @Test
    public void getForecastForDate() {
        //Given
        URI uri = UriComponentsBuilder.fromHttpUrl("https://dark-sky.p.rapidapi.com/" + 100.0 + "," + 200.0)
                .queryParam("units", "si")
                .build().encode().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-rapidapi-host", "dark-sky.p.rapidapi.com");
        headers.add("x-rapidapi-key", adminConfig.getWeatherApiKey());
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        List<Forecast> forecasts = new ArrayList<>();
        forecasts.add(new Forecast(200l, 100.1));
        Daily daily = new Daily(forecasts);
        Response response = new Response(daily);
        ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(uri, HttpMethod.GET, entity, Response.class)).thenReturn(responseEntity);
        WeatherService weatherService = new WeatherService();
        weatherService.setAdminConfig(adminConfig);
        weatherService.setRestTemplate(restTemplate);

        //When
        WeatherForecast result = weatherService.getForecastForDate(new Date(200l), 100, 200);
        //Then
        Assert.assertEquals(100.1 ,result.getTemperature(), 0.1);
    }
}