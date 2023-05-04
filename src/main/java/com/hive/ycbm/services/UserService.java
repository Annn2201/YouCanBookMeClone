package com.hive.ycbm.services;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.model.User;
public interface UserService {
    void save(User user);
    void updateById(Long id);
    void delete(User user);
    UserDto findById(Long id);
    UserDto findByMainEmail(String email);
}
