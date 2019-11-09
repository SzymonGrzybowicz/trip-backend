package com.kodilla.tripbackend.domains;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TRIPS")
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Trip {

    public Trip(Date date, double distance, List<Localization> localizations) {
        this.date = date;
        this.distance = distance;
        this.localizations = localizations;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TRIP_ID")
    private Long id;

    @Setter
    @OneToMany(
            targetEntity = Localization.class,
            mappedBy = "trip",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Localization> localizations;

    @Setter
    @Column(name = "DATE")
    private Date date;

    @Setter
    @Column(name = "DISTANCE")
    private double distance;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "trips")
    private List<User> usersSignedUp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CREATOR_USER_ID")
    @Setter
    private User creator;

    @Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    WeatherForecast weatherForecast;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return id.equals(trip.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
