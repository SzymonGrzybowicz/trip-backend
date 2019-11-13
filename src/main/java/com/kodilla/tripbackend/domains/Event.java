package com.kodilla.tripbackend.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@NamedNativeQuery(
        name = "Event.getEventByLocationRadius",
        query = "select E.* from  events E where " +
                "E.localization_localization_id in " +
                "( select L.localization_id from localizations L " +
                "where   L.number_in_trip = -1 " +
                "and ST_Distance_Sphere( point(L.longitude, L.latitude), point(:LONGITUDE, :LATITUDE) ) < :RADIUS * 1000);",
        resultClass = Event.class
)

@NamedNativeQuery(
        name = "Event.getEventUserJoined",
        query = "select E.* from  events E where E.event_id in ( select JT.event_id from join_user_events JT where JT.user_id = :USER_ID);",
        resultClass = Event.class
)
@Entity
@Table(name = "EVENTS" )
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Event {

        public Event(Date date, Localization localization, double price) {
            this.date = date;
            this.localization = localization;
            this.price = price;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "EVENT_ID" )
        private Long id;

        @Setter
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private Localization localization;

        @Setter
        @Column(name = "DATE" )
        private Date date;

        @Setter
        @Column(name = "PRICE")
        private double price;

        @ManyToMany(cascade = CascadeType.ALL, mappedBy = "events" )
        private List<User> usersSignedUp;

        @ManyToOne
        @JoinColumn(name = "CREATOR_USER_ID" )
        @Setter
        private User creator;

        @Setter
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private WeatherForecast weatherForecast;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id.equals(event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
