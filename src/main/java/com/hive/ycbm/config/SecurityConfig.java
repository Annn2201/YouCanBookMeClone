package com.hive.ycbm.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/confirm/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/create/**").permitAll()
                        .requestMatchers("/user/**").hasRole("ADMIN")
                        .requestMatchers("/homepage/**").permitAll()
                        .requestMatchers("/showFormForEdit/**").hasRole("ADMIN")
                        .requestMatchers("//bookingPage/**").hasRole("ADMIN")
                        .requestMatchers("/saveBookingPage/**").hasRole("ADMIN")
                        .requestMatchers("/**").hasRole("ADMIN")
                        .requestMatchers("/update-password/**").hasRole("ADMIN")
                        .anyRequest().denyAll())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/user"))
                .logout((logout) -> logout.permitAll());
                return http.build();
    }
}
