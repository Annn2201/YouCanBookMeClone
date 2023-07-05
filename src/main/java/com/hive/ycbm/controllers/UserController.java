package com.hive.ycbm.controllers;
import com.hive.ycbm.config.JwtUtilities;
import com.hive.ycbm.dto.UpdatePasswordDTO;
import com.hive.ycbm.services.UserService;
import com.hive.ycbm.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JwtUtilities jwtUtilities;
    @GetMapping("/user")
    public String showEditPage(Model model,
                               HttpServletRequest request) {
        UserDto currentUser = userService.loadCurrentUser(request);
        model.addAttribute("currentUser", currentUser);
        return "user";
    }
    @PostMapping("/user")
    public String editProfile(@ModelAttribute("currentUser") UserDto userDto) {
        userService.update(userDto);
        return "redirect:/admin/user?success";
    }
    @GetMapping("/update-password")
    public String showUpdataPasswordPage(@ModelAttribute("currentUser") UserDto userDto,
                                         Model model) {
        return "update-password";
    }
    @PostMapping("/update-password")
    public String updatePassword(@ModelAttribute("currentUser") UserDto userDto,
                                 UpdatePasswordDTO updatePasswordDTO,
                                 HttpServletRequest request,
                                 Model model) {
        if (!userService.checkIfValidOldPassword(updatePasswordDTO.getPassword(), request)) {
            return "redirect:/admin/update-password?invalid";
        }
        userService.changePassword(userService.loadCurrentMailEmail(request), updatePasswordDTO.getNewPassword());
        return "redirect:/admin/update-password?success";
    }
}
