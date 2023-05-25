package com.hive.ycbm.dto;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookerDto implements Serializable {
    private Long bookerId;
    private String firstName;
    private String lastName;
    private String email;
    private Date startTime;
}
