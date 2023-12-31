package com.hive.ycbm.dto;

import com.hive.ycbm.models.Booker;
import com.hive.ycbm.models.Calendar;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDto implements Serializable {
    private Long eventId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
    private String eventTitle;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;
    private Duration duration;
    private Booker booker;
    private Calendar calendar;


}
