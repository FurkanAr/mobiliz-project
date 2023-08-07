package com.mobiliz.security.service;


import com.mobiliz.exception.messages.Messages;
import com.mobiliz.model.User;
import com.mobiliz.repository.UserRepository;
import com.mobiliz.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(getClass());

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername method started");
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(Messages.User.NOT_EXISTS + username));
        logger.info("User found with username: {}", username);
        logger.info("loadUserByUsername method successfully worked");
        return CustomUserDetails.create(user);
    }
}
