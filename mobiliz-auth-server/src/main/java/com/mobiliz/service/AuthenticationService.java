package com.mobiliz.service;


import com.mobiliz.constants.Constant;
import com.mobiliz.converter.UserConverter;
import com.mobiliz.exception.messages.Messages;
import com.mobiliz.exception.user.UserEmailAlreadyInUseException;
import com.mobiliz.exception.user.UserNameAlreadyInUseException;
import com.mobiliz.model.User;
import com.mobiliz.repository.UserRepository;
import com.mobiliz.request.LoginRequest;
import com.mobiliz.request.UserRequest;
import com.mobiliz.security.jwt.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;


@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    Logger logger = LoggerFactory.getLogger(getClass());

    public AuthenticationService(UserRepository userRepository, UserConverter userConverter, AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    public String register(UserRequest userRequest) {
        logger.info("register method started");
        logger.info("UserRequest: {}", userRequest);

        checkRequestEmailInUse(userRequest);
        checkRequestUserNameInUse(userRequest);

        User user = userRepository.save(userConverter.convert(userRequest));

        logger.info("User created: {}", user.getId());

        return Constant.Authentication.REGISTRATION_MESSAGE;
    }

    public String login(LoginRequest loginRequest) {
        logger.info("login method started");
        User user = userRepository.findByUserName(loginRequest.getUserName()).orElseThrow(() ->
                new UsernameNotFoundException(Messages.User.NOT_EXISTS + loginRequest.getUserName()));

        logger.info("Found user: {}", user.getId());

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUserName(), loginRequest.getPassword());

        authenticationManager.authenticate(authToken);
        logger.info("User authenticated user: {}", user.getId());

        var token = jwtTokenService.generateToken(user);
        logger.info("User {}, token created ", user.getId());

        logger.info("login method successfully worked");
        return token;
    }

    private void checkRequestUserNameInUse(UserRequest userRequest) {
        Optional<User> user = userRepository.findByUserName(userRequest.getUserName());
        if (user.isPresent()) {
            logger.warn("Username is not available: {}", userRequest.getUserName());
            throw new UserNameAlreadyInUseException(Messages.User.NAME_EXIST + userRequest.getUserName());
        }
        logger.info("Username can be use");
    }

    private void checkRequestEmailInUse(UserRequest userRequest) {
        Optional<User> user = userRepository.findByEmail(userRequest.getEmail());
        if (user.isPresent()) {
            logger.warn("User already has account by given email: {}", userRequest.getEmail());
            throw new UserEmailAlreadyInUseException(Messages.User.EMAIL_EXIST + userRequest.getEmail());
        }
        logger.info("Email can be use");
    }

}
