package com.hive.ycbm.controllers;

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
    public String checkInfo() {
        return "connect-calendar";
    }

    @GetMapping("/oauth2")
    public String connectGoogle() {
        String authorizationUrl = googleCalendarService.createUri();
        return "redirect:" + authorizationUrl;
    }

    @GetMapping("/oauth2/google")
    public String handleGoogleCallback(@RequestParam("code") String code, HttpServletRequest request) {
        String accessToken = googleCalendarService.exchangeCodeForAccessToken(code);
        userService.saveAccessToken(accessToken, userService.loadCurrentMailEmail(request));
        return "redirect:/admin/";
    }
}