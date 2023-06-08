package com.hive.ycbm.services;

import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.User;

public interface UserService {
    void save(User user);

    void updateById(Long id);

    void delete(User user);

    UserDto findById(Long id);

    UserDto findByMainEmail(String email);

    void update(UserDto userDto);

    UserDto loadCurrentUser();

    void changePassword(String email, String newPassword);

    boolean checkIfValidOldPassword(String password);

    String loadCurrentMailEmail();

    void updateResetPasswordToken(String token, String email);

    void sendResetPasswordEmail(String recipientEmail, String link);

    UserDto getByResetPasswordToken(String token);

    void saveOauth2(String email, String name);
}
