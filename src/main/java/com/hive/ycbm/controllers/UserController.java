package com.hive.ycbm.controllers;
import com.hive.ycbm.dto.ResetPasswordDto;
import com.hive.ycbm.dto.UpdatePasswordDTO;
import com.hive.ycbm.services.UserService;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/api/v1/admin")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/user")
    public String showEditPage(Model model) {
        UserDto currentUser = userService.loadCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "user";
    }

    @PostMapping("/user")
    public String editProfile(@ModelAttribute("currentUser") UserDto userDto) {
        userService.update(userDto);
        return "redirect:/api/v1/admin/user?success";
    }

    @GetMapping("/update-password")
    public String showUpdataPasswordPage(@ModelAttribute("currentUser") UserDto userDto,
                                         Model model) {
        return "update-password";
    }
    @PostMapping("/update-password")
    public String updatePassword(@ModelAttribute("currentUser") UserDto userDto,
                                 UpdatePasswordDTO updatePasswordDTO,
                                 Model model) {
        if (!userService.checkIfValidOldPassword(updatePasswordDTO.getPassword())) {
            return "redirect:/api/v1/admin/update-password?invalid";
        }
        userService.changePassword(userService.loadCurrentMailEmail(), updatePasswordDTO.getNewPassword());
        return "redirect:/api/v1/admin/update-password?success";
    }
}
