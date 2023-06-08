package com.hive.ycbm.services.impl;

import com.hive.ycbm.exceptions.CustomException;
import com.hive.ycbm.models.User;
import com.hive.ycbm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        User user = userRepository.findByMainEmail(usernameOrEmail).orElse(null);
        if (user == null) {
            throw new CustomException("Invalid email or password", HttpStatus.NOT_FOUND);
        }
        return new org.springframework.security.core.userdetails.User(user.getMainEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()));
    }
}
