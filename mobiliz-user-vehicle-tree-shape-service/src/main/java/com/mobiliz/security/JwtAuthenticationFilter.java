package com.mobiliz.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtService;
    Logger logger = LoggerFactory.getLogger(getClass());


    public JwtAuthenticationFilter(JwtTokenService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("doFilterInternal method started");

        final String header = request.getHeader("Authorization");
        final String jwt;
        final String userName;
        if (header == null || !header.startsWith("Bearer ")) {
            logger.warn("Header is missing");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = header.substring(7);
        logger.info("jwt: {}", jwt);
        logger.info("Authorization request in header");
        userName = jwtService.getClaims(jwt).get("name").toString();
        logger.info("userName: {}", userName);
        String user = jwtService.findUserName(jwt);
        logger.info("user: {}", user);

        if (userName != null) {
            String authority = jwtService.getClaims(jwt).get("role").toString();
            logger.info("authority: {}", authority);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userName, null, List.of(new SimpleGrantedAuthority(authority)));
            logger.info("auth: {}", auth);

            SecurityContextHolder.getContext().setAuthentication(auth);
            logger.info("User: {}, authenticated", userName);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        logger.info("doFilterInternal method successfully worked");
        filterChain.doFilter(request, response);

    }
}
