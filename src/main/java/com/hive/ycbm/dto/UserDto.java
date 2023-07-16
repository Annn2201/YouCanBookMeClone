package com.hive.ycbm.dto;
import com.hive.ycbm.models.Role;
import lombok.*;

import java.util.List;

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
    private String resetPasswordToken;
    private String accessToken;
    private String refreshToken;
    private List<Role> roles;
}
