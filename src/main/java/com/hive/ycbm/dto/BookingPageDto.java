package com.hive.ycbm.dto;
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
}
