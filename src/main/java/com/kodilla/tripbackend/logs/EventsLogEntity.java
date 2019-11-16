package com.kodilla.tripbackend.logs;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "EVENTS_LOGS_ENTITY")
@NoArgsConstructor
public class EventsLogEntity {

    public EventsLogEntity(Date date, long numberOfEvents, long timeReading) {
        this.date = date;
        this.numberOfEvents = numberOfEvents;
        this.timeReading = timeReading;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EVENTS_LOGS_ENTITY_ID")
    private Long id;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "NUMBER_OF_EVENTS")
    private long numberOfEvents;

    @Column(name = "TIME_READING")
    private long timeReading;

}
