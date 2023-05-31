package com.hive.ycbm.dto;
import jakarta.persistence.Column;
import lombok.*;
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
}
