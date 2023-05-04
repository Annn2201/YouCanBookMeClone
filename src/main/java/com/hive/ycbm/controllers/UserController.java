package com.hive.ycbm.controllers;
import com.hive.ycbm.services.UserService;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user,
                        BindingResult result,
                        Model model) {
        UserDto userLogined = userService.findByMainEmail(user.getMainEmail());
        if (userLogined.getMainEmail() == null) {
            result.rejectValue("mainEmail", null, "Invalid user");
        }
        if (result.hasErrors()) {
            return "/login";
        }
        model.addAttribute("user", userLogined);
        return "user";
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
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "/register";
        }
        userService.save(user);
        return "redirect:/register?success";
    }
    @GetMapping("/user")
    public String showUpdatePage(
            Model model) {
        return "user";
    }
}
