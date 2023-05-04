package com.hive.ycbm.dto;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarDto {
    private Long calendarId;
    private String name;
    private String calendarEmail;
}
