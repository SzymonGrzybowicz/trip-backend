package com.kodilla.tripbackend.service;

import com.kodilla.tripbackend.AdminConfig;
import com.kodilla.tripbackend.domains.LocalizationDTO;
import com.kodilla.tripbackend.google_maps_json.geolocation.Geometry;
import com.kodilla.tripbackend.google_maps_json.geolocation.Location;
import com.kodilla.tripbackend.google_maps_json.geolocation.Response;
import com.kodilla.tripbackend.google_maps_json.geolocation.Result;
import com.kodilla.tripbackend.google_maps_json.places.Prediction;
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
    AdminConfig adminConfig;

    @Autowired
    RestTemplate restTemplate;

    public List<LocalizationDTO> getSuggestions(String input) {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/place/autocomplete/json?")
                .queryParam("input", input)
                .queryParam("key", adminConfig.getGoogleApiKey()).build().encode().toUri();

        Prediction prediction = restTemplate.getForObject(
                uri,
                Prediction.class);

        return prediction.getPlaces().stream()
                .limit(5)
                .map(e -> new LocalizationDTO(e.getPlaceId(),
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
}
