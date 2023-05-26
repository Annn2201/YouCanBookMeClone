package com.hive.ycbm.controllers;
import com.hive.ycbm.services.UserService;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@Controller
public class UserController {
    @Autowired
    private UserService userService;
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
                           @RequestParam ("confirmPassword") String confirmPassword,
                           BindingResult result,
                           Model model) {
        UserDto existedUser = userService.findByMainEmail(user.getMainEmail());
        if (existedUser.getMainEmail() != null) {
            result.rejectValue("mainEmail", null,
                    "Email is used !!!");
        }
        if (user.getPassword().length() < 6) {
            result.rejectValue("password", null,
                    "Your password must have 6 characters or more!!");
        }
        if (!user.getPassword().equals(confirmPassword)) {
           return "redirect:/register?error";
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "/register";
        }
        userService.save(user);
        return "redirect:/register?success";
    }
    @GetMapping("/user")
    public String showEditPage(Model model) {
        UserDto currentUser = userService.loadCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "user";
    }

    @PostMapping("/user")
    public String editProfile(@ModelAttribute("currentUser") UserDto userDto) {
        userService.update(userDto);
        return "redirect:/user?success";
    }

    @GetMapping("/update-password")
    public String showUpdataPasswordPage(@ModelAttribute("currentUser") UserDto userDto) {
        return "updatePassword";
    }

    @PostMapping("/update-password")
    public String updatePassword(
                                 @RequestParam(name = "password", required = false) String password,
                                 @RequestParam(name = "newPassword", required = false) String newPassword,
                                 @RequestParam(name = "confirmNewPassword", required = false) String confirmNewPassword) {
        if (!newPassword.equals(confirmNewPassword)) {
            return "redirect:/update-password?error";
        }
        if (!userService.checkIfValidOldPassword(password)) {
            return "redirect:/update-password?invalid";
        }
        if (newPassword.length() < 6) {
            return "redirect:/update-password?short";
        }
        userService.changePassword(newPassword);
        return "redirect:/update-password?success";
    }

}
