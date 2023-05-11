package com.hive.ycbm.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.Duration;
import java.util.Collection;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;
    private Date start;
    private Duration duration;
    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;
    @ManyToOne
    private Booker booker;
    @ManyToMany(mappedBy = "events")
    private Collection<User> users;
}
