package com.kodilla.tripbackend.service;

import com.kodilla.tripbackend.AdminConfig;
import com.kodilla.tripbackend.domains.LocalizationDto;
import com.kodilla.tripbackend.google_maps_json.distanceMatrix.Distance;
import com.kodilla.tripbackend.google_maps_json.distanceMatrix.DistanceResponse;
import com.kodilla.tripbackend.google_maps_json.distanceMatrix.Element;
import com.kodilla.tripbackend.google_maps_json.distanceMatrix.Row;
import com.kodilla.tripbackend.google_maps_json.geolocation.Geometry;
import com.kodilla.tripbackend.google_maps_json.geolocation.Location;
import com.kodilla.tripbackend.google_maps_json.geolocation.Response;
import com.kodilla.tripbackend.google_maps_json.geolocation.Result;
import com.kodilla.tripbackend.google_maps_json.places.Place;
import com.kodilla.tripbackend.google_maps_json.places.Prediction;
import com.kodilla.tripbackend.google_maps_json.places.StructuredFormatting;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GoogleMapServiceTestSuite {

    @Mock
    AdminConfig adminConfig;

    @Mock
    RestTemplate restTemplate;

    @Test
    public void getSuggestions() {
        //Given
        GoogleMapService googleMapService = new GoogleMapService();
        googleMapService.setAdminConfig(adminConfig);
        googleMapService.setRestTemplate(restTemplate);
        Prediction prediction = new Prediction();
        prediction.getPlaces().add(new Place("aaa", new StructuredFormatting("main", "secondary")));
        prediction.getPlaces().add(new Place("aaa2", new StructuredFormatting("main2", "secondary2")));
        URI uri = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/place/autocomplete/json?")
                .queryParam("input", "war")
                .queryParam("key", adminConfig.getGoogleApiKey()).build().encode().toUri();
        Mockito.when(restTemplate.getForObject(uri, Prediction.class)).thenReturn(prediction);
        //When
        List<LocalizationDto> result = googleMapService.getSuggestions("war");
        //Then
        Assert.assertEquals(2, result.size());
        Assert.assertEquals("main" ,result.get(0).getMainDescription());
        Assert.assertEquals("secondary2", result.get(1).getSecondaryDescription());
    }

    @Test
    public void getCoordinates() {
        //Given
        URI uri = UriComponentsBuilder.fromHttpUrl("https://maps.google.com/maps/api/geocode/json?")
                .queryParam("place_id", "aaa")
                .queryParam("key", adminConfig.getGoogleApiKey()).build().encode().toUri();
        Location location = new Location(0.0, 1.1);
        Geometry geometry = new Geometry(location);
        Result locationResult = new Result(geometry);
        List<Result> results = new ArrayList<>();
        results.add(locationResult);
        Response response = new Response(results);
        Mockito.when(restTemplate.getForObject(uri, Response.class)).thenReturn(response);
        GoogleMapService googleMapService = new GoogleMapService();
        googleMapService.setRestTemplate(restTemplate);
        googleMapService.setAdminConfig(adminConfig);
        //When
        Location result = googleMapService.getCoordinates("aaa");
        //Then
        Assert.assertEquals(0.0, result.getLatitude(), 0.1);
        Assert.assertEquals(1.1, result.getLongitude(), 0.1);
    }

    @Test
    public void getDistance() {
        //Given
        URI uri = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/distancematrix/json?")
                .queryParam("units", "si")
                .queryParam("origins", "place_id:" + "aaa")
                .queryParam("destinations", "place_id:" + "bbb")
                .queryParam("key", adminConfig.getGoogleApiKey()).build().encode().toUri();
        Distance distance = new Distance("100");
        Element element = new Element(distance);
        List<Element> elements = new ArrayList<>();
        elements.add(element);
        Row row = new Row(elements);
        List<Row> rows = new ArrayList<>();
        rows.add(row);
        DistanceResponse distanceResponse = new DistanceResponse(rows);
        Mockito.when(restTemplate.getForObject(uri, DistanceResponse.class)).thenReturn(distanceResponse);
        GoogleMapService googleMapService = new GoogleMapService();
        googleMapService.setAdminConfig(adminConfig);
        googleMapService.setRestTemplate(restTemplate);
        //When
        String result = googleMapService.getDistance("aaa", "bbb");
        //Then
        Assert.assertEquals("100", result);
    }
}