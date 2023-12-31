package com.hive.ycbm.dto;
import com.hive.ycbm.models.Calendar;
import com.hive.ycbm.models.User;
import jakarta.persistence.Column;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingPageDto {
    private Long pageId;
    private String title;
    private String intro;
    @Column(unique = true)
    private  String bookingLink;
    private Calendar calendar;
    private User user;
}
