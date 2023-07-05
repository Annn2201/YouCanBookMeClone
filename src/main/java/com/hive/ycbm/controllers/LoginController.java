package com.hive.ycbm.controllers;

import com.hive.ycbm.config.JwtUtilities;
import com.hive.ycbm.dto.LoginDto;
import com.hive.ycbm.dto.ResetPasswordDto;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.Role;
import com.hive.ycbm.models.User;
import com.hive.ycbm.services.BookingPageService;
import com.hive.ycbm.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtilities jwtUtilities;
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    @PostMapping("/login")
    public String login(LoginDto loginDto,
                        HttpServletResponse response,
                        Model model) {
        UserDto loginUser = userService.findByMainEmail(loginDto.getMainEmail());
        if (loginUser == null) {
            return "redirect:/login?invalid";
        }
        List<String> roleNames = new ArrayList<>();
        for (Role role: loginUser.getRoles()) {
            roleNames.add(role.getName());
        }
        String jwtToken = jwtUtilities.generateToken(loginDto.getMainEmail(), roleNames);
        Cookie jwtCookie = new Cookie("jwt", jwtToken);
        response.addCookie(jwtCookie);
        model.addAttribute("currentUser", loginUser);
        return "redirect:/admin/";
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
                           Model model,
                           HttpServletResponse response) {
        UserDto existedUser = userService.findByMainEmail(user.getMainEmail());
        if (existedUser.getMainEmail() != null) {
            result.rejectValue("mainEmail", null,
                    "Email is used !!!");
            model.addAttribute("user", user);
            return "/register";

        }
        List<String> roleNames = new ArrayList<>();
        for (Role role: user.getRoles()) {
            roleNames.add(role.getName());
        }
        String jwtToken = jwtUtilities.generateToken(user.getMainEmail(), roleNames);
        Cookie jwtCookie = new Cookie("jwt", jwtToken);
        response.addCookie(jwtCookie);
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
    @GetMapping("/login/google")
    public String loginWithGoogle() {
        return "redirect:/oauth2/authorization/google";
    }
}
