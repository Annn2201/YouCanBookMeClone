package com.hive.ycbm.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booker")
public class Booker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booker_id")
    private Long bookerId;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private Date startTime;
    @OneToMany
    @JoinColumn(name = "event_id")
    private List<Event> events;

}
