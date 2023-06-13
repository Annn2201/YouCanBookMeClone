package com.hive.ycbm.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "calendar")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    private Long calendarId;
    private String name;
    private String calendarEmail;
    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL)
    private Collection<Event> events;
    @OneToOne
    @JoinColumn(name = "booking_page_id")
    private BookingPage bookingPage;
}
