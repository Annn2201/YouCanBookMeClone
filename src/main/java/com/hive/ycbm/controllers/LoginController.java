package com.hive.ycbm.controllers;

import com.hive.ycbm.dto.ResetPasswordDto;
import com.hive.ycbm.dto.UpdatePasswordDTO;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.User;
import com.hive.ycbm.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user,
                           BindingResult result,
                           Model model) {
        UserDto existedUser = userService.findByMainEmail(user.getMainEmail());
        if (existedUser.getMainEmail() != null) {
            result.rejectValue("mainEmail", null,
                    "Email is used !!!");
        }
        userService.save(user);
        return "redirect:/register?success";
    }
    @GetMapping("/forget-password")
    public String showForgotPage() {
        return "forget-password";
    }

    @PostMapping("/forget-password")
    public String forgetPassword(@RequestParam("email") String email,
                                 HttpServletRequest request) {
        if (userService.findByMainEmail(email).equals(null)) {
            return "redirect:/forget-password?error";
        }
        String token = RandomStringUtils.random(30);
        String siteURL = request.getRequestURL().toString();
        String newSiteURL = siteURL.replace(request.getRequestURI(), "").toString();
        userService.updateResetPasswordToken(token, email);
        String resetPasswordLink = newSiteURL + "/reset-password?token=" + token;
        userService.sendResetPasswordEmail(email, resetPasswordLink);
        return "redirect:/forget-password?success";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@Param(value = "token") String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(ResetPasswordDto resetPasswordDto,
                                @ModelAttribute("token") String token) {
        UserDto userDto = userService.getByResetPasswordToken(token);
        if (!resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword())) {
            return "redirect:/update-password?error";
        }
        if (resetPasswordDto.getPassword().length() < 6) {
            return "redirect:/update-password?short";
        }
        userService.changePassword(userDto.getMainEmail(),resetPasswordDto.getPassword());
        return "redirect:/reset-password?success";
    }

}
