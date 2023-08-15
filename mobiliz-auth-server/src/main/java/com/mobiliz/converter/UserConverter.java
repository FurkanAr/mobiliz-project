package com.mobiliz.converter;

import com.mobiliz.model.User;
import com.mobiliz.model.UserRole;
import com.mobiliz.request.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserConverter {

    private final PasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(getClass());

    public UserConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User convert(UserRequest userRequest) {
        logger.info("convert to User method started");
        User user = new User();
        user.setName(userRequest.getName());
        user.setSurName(userRequest.getSurName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setRole(UserRole.valueOf(userRequest.getRole()));
        user.setCompanyId(userRequest.getCompanyId());
        user.setCompanyName(userRequest.getCompanyName());
        user.setCompanyFleetId(userRequest.getFleetId());
        user.setCompanyFleetName(userRequest.getCompanyFleetName());

        logger.info("convert to User method successfully worked");
        return user;
    }

}
