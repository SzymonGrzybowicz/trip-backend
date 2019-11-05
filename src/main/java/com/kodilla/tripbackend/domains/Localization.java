package com.kodilla.tripbackend.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties
@Getter
@Table(name = "LOCALIZATIONS")
@NoArgsConstructor
public class Localization {

    public Localization(String mainDescription, String secondaryDescription, double longitude, double latitude, int numberInTrip, String googleId) {
        this.mainDescription = mainDescription;
        this.secondaryDescription = secondaryDescription;
        this.longitude = longitude;
        this.latitude = latitude;
        this.numberInTrip = numberInTrip;
        this.googleId = googleId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LOCALIZATION_ID")
    private Long id;

    @Column(name = "MAIN_DESCRIPTION")
    private String mainDescription;

    @Column(name = "SECONDARY_DESCRIPTION")
    private String secondaryDescription;

    @Column(name = "LONGITUDE")
    private double longitude;

    @Column(name = "LATITUDE")
    private double latitude;

    @Column(name = "NUMBER_IN_TRIP")
    private int numberInTrip;

    @Column(name = "GOOGLE_ID")
    private String googleId;

    @ManyToOne(cascade = CascadeType.ALL)
    @Setter
    private Trip trip;
}
