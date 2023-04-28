package com.hive.ycbm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

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
    private Long bookerID;
    private String firstName;
    private String lastName;
    private String email;
    private Date startTime;
    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
