package com.hive.ycbm.services.impl;

import com.hive.ycbm.config.JwtUtilities;
import com.hive.ycbm.exceptions.CustomException;
import com.hive.ycbm.models.CustomOAuth2User;
import com.hive.ycbm.repositories.RoleRepository;
import com.hive.ycbm.repositories.UserRepository;
import com.hive.ycbm.services.UserService;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.Role;
import com.hive.ycbm.models.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtilities jwtUtilities;
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void save(User user) {
        Role role = roleRepository.findByName("ADMIN");
        if (role == null) {
            role = roleRepository.save(new Role("ADMIN"));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(user.getConfirmPassword()));
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
                .resetPasswordToken(user.getResetPasswordToken())
                .build();
    }
    @Override
    public UserDto findByMainEmail(String email) {
        User user = userRepository.findByMainEmail(email).orElse(new User());
        return UserDto.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mainEmail(user.getMainEmail())
                .phone(user.getPhone())
                .organization(user.getOrganization())
                .password(user.getPassword())
                .resetPasswordToken(user.getResetPasswordToken())
                .roles(user.getRoles())
                .build();
    }
    @Override
    public void update(UserDto userDto) {
        User updateUser = userRepository.findByMainEmail(userDto.getMainEmail()).orElse(new User());
        updateUser.setFirstName(userDto.getFirstName());
        updateUser.setLastName(userDto.getLastName());
        updateUser.setPhone(userDto.getPhone());
        updateUser.setOrganization(userDto.getOrganization());
        userRepository.save(updateUser);
    }
    @Override
    public UserDto loadCurrentUser(HttpServletRequest request) {
        return findByMainEmail(loadCurrentMailEmail(request));
    }
    @Override
    public String loadCurrentMailEmail(HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String mail = null;
        if (principal instanceof CustomOAuth2User) {
            mail = ((CustomOAuth2User) principal).getAttribute("email");
        } else {
            String token = jwtUtilities.extractToken(request);
            mail = jwtUtilities.extractUsername(token);
        }
        return mail;
    }
    @Override
    public boolean checkIfValidOldPassword(String password, HttpServletRequest request) {
        String email = loadCurrentMailEmail(request);
        if (password == "" && findByMainEmail(email).getPassword() == null){
            return true;
        }
        else if (!passwordEncoder.matches(password, loadCurrentUser(request).getPassword())) {
            return false;
        }
        return true;
    }
    @Override
    public void changePassword(String email, String newPassword) {
        User userWannaChangePassword = userRepository.findByMainEmail(email).orElse(null);
        userWannaChangePassword.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userWannaChangePassword);
    }
    @Override
    public void updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByMainEmail(email).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("Your email is wrong! Please enter again!");
        }
        user.setResetPasswordToken(token);
        userRepository.save(user);
    }
    @Override
    public UserDto getByResetPasswordToken(String token) {
        User user = userRepository.findByResetPasswordToken(token).orElse(null);
        return UserDto.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mainEmail(user.getMainEmail())
                .phone(user.getPhone())
                .organization(user.getOrganization())
                .password(user.getPassword())
                .resetPasswordToken(user.getResetPasswordToken())
                .build();
    }
    @Override
    public void sendResetPasswordEmail(String recipientEmail, String link) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("ycbmclone@gmail.com", "ycbm's supporter");
            helper.setTo(recipientEmail);
            String subject = "Here's the link to reset your password";
            String content = "<p>Hello,</p>"
                    + "<p>You have requested to reset your password.</p>"
                    + "<p>Click the link below to change your password:</p>"
                    + "<p><a href=\"" + link + "\">Change my password</a></p>"
                    + "<br>"
                    + "<p>Ignore this email if you do remember your password, "
                    + "or you have not made the request.</p>";
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new CustomException("Error with MineMessage", HttpStatus.BAD_REQUEST);
        }
    }
}
