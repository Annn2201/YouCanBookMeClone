package com.hive.ycbm.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePasswordDTO {
    private Long userId;
    private String password;
    private String newPassword;
    private String confirmNewPassword;
}
