package com.kodilla.tripbackend.service;

import com.kodilla.tripbackend.AdminConfig;
import com.kodilla.tripbackend.domains.LocalizationDto;
import com.kodilla.tripbackend.google_maps_json.distanceMatrix.DistanceResponse;
import com.kodilla.tripbackend.google_maps_json.geolocation.Location;
import com.kodilla.tripbackend.google_maps_json.geolocation.Response;
import com.kodilla.tripbackend.google_maps_json.places.Prediction;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GoogleMapService {

    @Autowired
    @Setter
    private AdminConfig adminConfig;

    @Autowired
    @Setter
    private RestTemplate restTemplate;

    public List<LocalizationDto> getSuggestions(String input) {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/place/autocomplete/json?")
                .queryParam("input", input)
                .queryParam("key", adminConfig.getGoogleApiKey()).build().encode().toUri();

        Prediction prediction = restTemplate.getForObject(
                uri,
                Prediction.class);

        return prediction.getPlaces().stream()
                .limit(5)
                .map(e -> new LocalizationDto(e.getPlaceId(),
                        e.getStructuredFormatting().getMainText(),
                        e.getStructuredFormatting().getSecondaryText(),
                        0))
                .collect(Collectors.toList());
    }

    public Location getCoordinates(String placeId) {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://maps.google.com/maps/api/geocode/json?")
                .queryParam("place_id", placeId)
                .queryParam("key", adminConfig.getGoogleApiKey()).build().encode().toUri();

        Response response = restTemplate.getForObject(uri, Response.class);

        return response.getResults().get(0).getGeometry().getLocation();
    }

    public String getDistance(String firstPlaceId, String secondPlaceId) {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/distancematrix/json?")
                .queryParam("units", "si")
                .queryParam("origins", "place_id:" + firstPlaceId)
                .queryParam("destinations", "place_id:" + secondPlaceId)
                .queryParam("key", adminConfig.getGoogleApiKey()).build().encode().toUri();
        DistanceResponse response = restTemplate.getForObject(uri, DistanceResponse.class);
        if (response.getRows().size() > 0 && response.getRows().get(0).getElements().size()> 0) {
            return response.getRows().get(0).getElements().get(0).getDistance().getDistanceInKm();
        }
        return "";
    }
}
