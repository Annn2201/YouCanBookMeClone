package com.hive.ycbm.dto;
import com.hive.ycbm.models.BookingPage;
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
    private BookingPage bookingPage;
}
