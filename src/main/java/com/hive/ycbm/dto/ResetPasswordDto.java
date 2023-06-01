package com.hive.ycbm.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordDto {
    private String password;
    private String confirmPassword;
}
