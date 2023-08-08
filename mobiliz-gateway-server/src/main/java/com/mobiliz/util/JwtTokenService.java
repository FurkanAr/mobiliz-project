package com.mobiliz.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenService {


    public static final long EXPIRES_IN = 1440000;
    public static final String SECRET_KEY = "9ln6jZ1h5BuP28k5RmlOaeL5rise7xe9czd4yA8pZdfGA36zbmRt";

    Logger logger = LoggerFactory.getLogger(getClass());

    public void validateToken(final String token) {
        logger.info("validateToken method started");
        Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
        logger.info("validateToken method successfully worked");
    }

    private Key getKey() {
        logger.info("getKey method started");
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        logger.info("getKey method successfully worked");
        return Keys.hmacShaKeyFor(key);
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

    public boolean isExpired(String token) {
        logger.info("isExpired method started");
        boolean isSame = !getClaims(token).getExpiration().before(new Date());
        logger.info("isExpired method successfully worked");
        return isSame;
    }

}
