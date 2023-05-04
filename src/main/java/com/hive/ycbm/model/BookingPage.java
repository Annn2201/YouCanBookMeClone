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
@Table(name = "booking_page")
public class BookingPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "page_id")
    private Long pageId;
    private String title;
    private String intro;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(mappedBy = "bookingPages")
    private Collection<Calendar> calendars;
}
