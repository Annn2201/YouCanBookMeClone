package com.hive.ycbm.services.impl;
import com.hive.ycbm.repositories.RoleRepository;
import com.hive.ycbm.repositories.UserRepository;
import com.hive.ycbm.services.UserService;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.Role;
import com.hive.ycbm.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void save(User user) {
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = roleRepository.save(new Role("ROLE_ADMIN"));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(role));
        userRepository.save(user);
    }
    @Override
    public void updateById(Long id) {
        User userUpdate = userRepository.findById(id).orElse(null);
        userUpdate.setFirstName(userUpdate.getFirstName());
        userUpdate.setLastName(userUpdate.getLastName());
        userUpdate.setPhone(userUpdate.getPhone());
        userUpdate.setOrganization(userUpdate.getOrganization());
        userRepository.save(userUpdate);
    }
    @Override
    public void delete(User user) {
        userRepository.deleteById(user.getUserId());
    }
    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElse(new User());
        return UserDto.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mainEmail(user.getMainEmail())
                .phone(user.getPhone())
                .organization(user.getOrganization())
                .build();
    }
    @Override
    public UserDto findByMainEmail(String email){
        User user = userRepository.findByMainEmail(email).orElse(new User());
        return UserDto.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mainEmail(user.getMainEmail())
                .phone(user.getPhone())
                .organization(user.getOrganization())
                .password(user.getPassword())
                .build();
    }
    @Override
    public void update(UserDto userDto) {
        User updateUser = userRepository.findByMainEmail(userDto.getMainEmail()).orElse(new User());
        updateUser.setPhone(userDto.getPhone());
        updateUser.setOrganization(userDto.getOrganization());
        userRepository.save(updateUser);
    }

    @Override
    public UserDto loadCurrentUser() {
        return findByMainEmail(loadCurrentMailEmail());
    }
    @Override
    public String loadCurrentMailEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails)){
            return null;
        }
        return ((UserDetails)principal).getUsername();
    }

    @Override
    public boolean checkIfValidOldPassword(String password) {
        if (!passwordEncoder.matches(password, loadCurrentUser().getPassword())) {
            return false;
        }
        return true;
    }
    @Override
    public void changePassword(String newPassword) {
        User userWannaChangePassword = userRepository.findByMainEmail(loadCurrentMailEmail()).orElse(null);
        userWannaChangePassword.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userWannaChangePassword);
    }
}
