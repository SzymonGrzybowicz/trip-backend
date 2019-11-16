package com.kodilla.tripbackend.logs;

import com.kodilla.tripbackend.domains.User;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "GOOGLE_LOGS_ENTITY")
@NoArgsConstructor
public class GoogleLogsEntity {

    public GoogleLogsEntity(Date date, User user) {
        this.date = date;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    Long id;

    @Column(name = "DATE")
    Date date;

    @OneToOne
    User user;

}
