package com.mobiliz.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    Logger logger = LoggerFactory.getLogger(getClass());

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        logger.info("securityFilterChain method started");
        httpSecurity
                .csrf()
                .disable()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.GET, "/api/vehicles")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/vehicles/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/vehicles").hasRole("ADMIN")
                .antMatchers("/api/companies/**").hasAuthority("ADMIN")
                .antMatchers("/api/user/**").hasAuthority("STANDARD")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        logger.info("securityFilterChain method successfully worked");

        return httpSecurity.build();
    }
}
