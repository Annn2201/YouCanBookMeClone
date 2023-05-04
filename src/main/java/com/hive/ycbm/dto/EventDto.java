package com.hive.ycbm.dto;
import lombok.*;

import java.sql.Date;
import java.time.Duration;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDto {
    private Long eventId;
    private Date start;
    private Duration duration;
}
