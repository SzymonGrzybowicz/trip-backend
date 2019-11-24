package com.kodilla.tripbackend.service;

import com.kodilla.tripbackend.AdminConfig;
import com.kodilla.tripbackend.dark_sky_weather_json.Forecast;
import com.kodilla.tripbackend.dark_sky_weather_json.Response;
import com.kodilla.tripbackend.domains.WeatherForecast;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class WeatherService {

    @Autowired
    @Setter
    private AdminConfig adminConfig;

    @Autowired
    @Setter
    private RestTemplate restTemplate;

    public WeatherForecast getForecastForDate(Date date, double latitude, double longitude) {
        List<Forecast> forecasts = getTemperatureForecast(latitude, longitude);
        for (Forecast forecast: forecasts) {
            Date forecastDate = new Date(forecast.getTime() * 1000);
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(forecastDate);
            cal2.setTime(date);
            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
            if (sameDay) {
                return new WeatherForecast(new Date(System.currentTimeMillis()), forecast.getTemperature());
            }
        }
        return null;
    }

    private List<Forecast> getTemperatureForecast(double latitude, double longitude) {

        URI uri = UriComponentsBuilder.fromHttpUrl("https://dark-sky.p.rapidapi.com/" + latitude + "," + longitude)
                .queryParam("units", "si")
                .build().encode().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-rapidapi-host", "dark-sky.p.rapidapi.com");
        headers.add("x-rapidapi-key", adminConfig.getWeatherApiKey());
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<Response> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Response.class);
        return response.getBody().getDaily().getData();
    }
}
