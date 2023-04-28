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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userID;
    private String firstName;
    private String lastName;
    private String mainEmail;
    private String password;
    private String phone;
    private String organization;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Collection<BookingPage> bookingPages;

    @ManyToMany
    @JoinTable(name = "user_event",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "booking_ref"))
    private Collection<Event> events;
}
