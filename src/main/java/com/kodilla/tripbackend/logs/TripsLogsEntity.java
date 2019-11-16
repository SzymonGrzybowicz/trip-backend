package com.kodilla.tripbackend.logs;

import com.kodilla.tripbackend.domains.Localization;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRIP_LOGS_ENTITY")
@NoArgsConstructor
public class TripsLogsEntity {

    public TripsLogsEntity(Date date, long numberOfTrips, long timeReading) {
        this.date = date;
        this.numberOfTrips = numberOfTrips;
        this.timeReading = timeReading;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TRIP_LOGS_ENTITY_ID")
    private Long id;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "NUMBER_OF_TRIPS")
    private long numberOfTrips;

    @Column(name = "TIME_READING")
    private long timeReading;
}
