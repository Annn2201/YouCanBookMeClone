package com.hive.ycbm.dto;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String mainEmail;
    private String phone;
    private String organization;
    private String password;
}
