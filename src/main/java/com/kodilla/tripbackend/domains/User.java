package com.kodilla.tripbackend.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "USER_NAME", unique = true,
            nullable = false, length = 45)
    private String username;

    @Setter
    @Column(name = "PASSWORD",
            nullable = false, length = 60)
    private String password;

    @Column(name = "ENABLED")
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "JOIN_USER_TRIPS",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TRIP_ID", referencedColumnName = "TRIP_ID")}
    )
    private List<Trip> trips;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "JOIN_USER_EVENTS",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "EVENT_ID", referencedColumnName = "EVENT_ID")}
    )
    private List<Event> events;

    @OneToMany(
            targetEntity = Trip.class,
            mappedBy = "creator",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Trip> createdTrips;

    @OneToMany(
            targetEntity = Event.class,
            mappedBy = "creator",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Event> createdEvents;

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        trips = new ArrayList<>();
        createdTrips = new ArrayList<>();
        createdEvents = new ArrayList<>();
        events = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
