package com.hive.ycbm.config;

import com.hive.ycbm.services.impl.CustomOAuth2UserService;
import com.hive.ycbm.services.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private String[] endPoints;
    @Autowired
    private JwtUtilities jwtUtilities;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    { return authenticationConfiguration.getAuthenticationManager();}
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/login/**",
                        "/register/**",
                        "/js/**",
                        "/css/**",
                        "/forget-password/**",
                        "/reset-password/**",
                        "/public/**",
                        "/create-event",
                        "/confirm").permitAll()
                .requestMatchers("/info/**").hasAnyRole("ADMIN")
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .and()
                        .successHandler(oAuth2AuthenticationSuccessHandler()))
                .logout(logout -> logout.permitAll()
                        .deleteCookies("jwt"));
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtilities, customUserDetailsService);
        http.addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CustomOAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new CustomOAuth2AuthenticationSuccessHandler();
    }
}
