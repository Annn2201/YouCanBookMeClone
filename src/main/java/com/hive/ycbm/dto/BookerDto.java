package com.hive.ycbm.dto;
import lombok.*;

import java.sql.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookerDto {
    private Long bookerId;
    private String firstName;
    private String lastName;
    private String email;
    private Date startTime;
}
