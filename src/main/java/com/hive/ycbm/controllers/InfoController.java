package com.hive.ycbm.controllers;

import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.services.UserService;
import com.hive.ycbm.services.impl.GoogleCalendarService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/info")
@RequiredArgsConstructor
public class InfoController {
    private final UserService userService;
    private final GoogleCalendarService googleCalendarService;

    @GetMapping("/")
    public String checkInfo(HttpServletRequest request) {
        UserDto userDto = userService.loadCurrentUser(request);
        if (userDto.getRefreshToken() == null) {
            return "connect-calendar";
        } else {
            String accessToken = googleCalendarService.refreshAccessToken(userDto.getRefreshToken());
            userService.saveAccessToken(accessToken, userDto.getMainEmail());
            return "redirect:/admin/";
        }
    }

    @GetMapping("/oauth2")
    public String connectGoogle(HttpServletRequest request) {
        String authorizationUrl = googleCalendarService.createUri(request);
        return "redirect:" + authorizationUrl;
    }

    @GetMapping("/oauth2/google")
    public String handleGoogleCallback(@RequestParam("code") String code, HttpServletRequest request) {
        UserDto userDto = userService.loadCurrentUser(request);
        String refreshToken = googleCalendarService.exchangeCodeForRefreshToken(code);
        userService.saveRefreshToken(refreshToken, userDto.getMainEmail());
        return "redirect:/admin/";
    }
}