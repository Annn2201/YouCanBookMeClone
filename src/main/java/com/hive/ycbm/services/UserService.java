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

    void changePassword(String newPassword);

    boolean checkIfValidOldPassword(String password);
    String loadCurrentMailEmail();
}
