package com.kodilla.tripbackend;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminConfig {

    @Value("${google.api.key}")
    private String googleApiKey;

    @Value("${weather.api.key}")
    private String weatherApiKey;
}
