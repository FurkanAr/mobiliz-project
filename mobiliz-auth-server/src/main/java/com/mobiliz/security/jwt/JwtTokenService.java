package com.mobiliz.security.jwt;

import com.mobiliz.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.Map.entry;

@Service
public class JwtTokenService {


    public static final long EXPIRES_IN = 1440000;
    public static final String SECRET_KEY = "9ln6jZ1h5BuP28k5RmlOaeL5rise7xe9czd4yA8pZdfGA36zbmRt";


    Logger logger = LoggerFactory.getLogger(getClass());

    public String findUserName(String token) {
        logger.info("findUserName method started");
        String userName = exportToken(token, Claims::getSubject);
        logger.info("User found with token: {}", userName);
        logger.info("findUserName method successfully worked");
        return userName;
    }

    private <T> T exportToken(String token, Function<Claims, T> claimFunction) {
        logger.info("exportToken method started");
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();
        logger.info("exportToken method successfully worked");
        return claimFunction.apply(claims);
    }

    private Key getKey() {
        logger.info("getKey method started");
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        logger.info("getKey method successfully worked");
        return Keys.hmacShaKeyFor(key);
    }

    public boolean tokenControl(String jwt, UserDetails userDetails) {
        logger.info("tokenControl method started");
        final String userName = findUserName(jwt);
        boolean isSame = (userName.equals(userDetails.getUsername()) && !exportToken(jwt, Claims::getExpiration).before(new Date()));
        logger.info("Username is same: {}", isSame);
        logger.info("tokenControl method successfully worked");
        return isSame;
    }

    public String generateToken(User user) {
        logger.info("generateToken method started");

        Map<String, String> claims = Map.ofEntries(entry("userId", user.getId().toString()),
                entry("name", user.getName()), entry("surname", user.getSurName()),
                entry("companyId", user.getCompanyId().toString()),
                entry("companyFleetId", user.getCompanyFleetId().toString()),
                entry("companyName", user.getCompanyName()),
                entry("companyFleetName", user.getCompanyFleetName()),
                entry("role", user.getRole().name())
        );


        String token = Jwts.builder()
                .setSubject(user.getUserName())
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRES_IN))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        logger.info("generateToken method successfully worked");
        return token;
    }


}
