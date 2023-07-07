package com.hive.ycbm.config;

import com.hive.ycbm.models.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collections;

public class CustomOAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private JwtUtilities jwtUtilities;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String email = ((CustomOAuth2User) authentication.getPrincipal()).getAttribute("email");
        String token = jwtUtilities.generateToken(email, Collections.singletonList("ROLE_ADMIN"));
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setPath("/");
        jwtCookie.setHttpOnly(true);
        response.addCookie(jwtCookie);
        response.sendRedirect("/admin/");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

