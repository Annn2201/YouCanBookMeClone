package com.hive.ycbm.services;

import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    void save(User user);

    void delete(User user);

    UserDto findByMainEmail(String email);

    void update(UserDto userDto);

    UserDto loadCurrentUser(HttpServletRequest request);

    void changePassword(String email, String newPassword);

    boolean checkIfValidOldPassword(String password, HttpServletRequest request);

    String loadCurrentMailEmail(HttpServletRequest request);

    void updateResetPasswordToken(String token, String email);

    void sendResetPasswordEmail(String recipientEmail, String link);

    UserDto getByResetPasswordToken(String token);

    void saveAccessToken(String token, String email);
}
