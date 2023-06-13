package com.hive.ycbm.controllers;

import com.hive.ycbm.models.CustomOAuth2User;
import com.hive.ycbm.dto.UserDto;
import com.hive.ycbm.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class OAuth2LoginController extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        UserDto existedUser = userService.findByMainEmail(oauthUser.getEmail());
        if (existedUser.getMainEmail() == null) {
            userService.saveOauth2(oauthUser.getEmail(), oauthUser.getName());
        }
        response.sendRedirect("/admin/");
    }
}
