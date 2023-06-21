package com.hive.ycbm.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Collection;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;
    private String eventTitle;
    private LocalDateTime end;
    private LocalDateTime start;
    private Duration duration;
    @ManyToOne()
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;
    @ManyToOne
    @JoinColumn(name = "booker_id")
    private Booker booker;
    @ManyToMany(mappedBy = "events")
    private Collection<User> user;



}
