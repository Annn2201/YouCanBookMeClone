package com.hive.ycbm.model;
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
    @ManyToMany
    @JoinTable(name = "calendar_page",
            joinColumns = @JoinColumn(name = "calendar_id"),
            inverseJoinColumns = @JoinColumn(name = "page_id"))
    private Collection<BookingPage> bookingPages;
}
