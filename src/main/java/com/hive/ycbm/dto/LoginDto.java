package com.hive.ycbm.dto;

import com.hive.ycbm.models.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    private String mainEmail;
    private String password;
    private List<Role> roles;
}
