package com.hive.ycbm.services.impl;

import com.hive.ycbm.models.CustomOAuth2User;
import com.hive.ycbm.models.Role;
import com.hive.ycbm.models.User;
import com.hive.ycbm.repositories.RoleRepository;
import com.hive.ycbm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        Optional<User> userOptional = userRepository.findByMainEmail(email);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = new User();
            user.setMainEmail(email);
            user.setFirstName(name);
            Role role = roleRepository.findByName("ROLE_ADMIN");
            if (role == null) {
                role = roleRepository.save(new Role("ROLE_ADMIN"));
            }
            user.setRoles(List.of(role));
            userRepository.save(user);
        }

        return new CustomOAuth2User(oAuth2User.getAttributes(), user);
    }
}