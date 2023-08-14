package com.mobiliz.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Key;
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
        logger.info("claims: {}", claims.getSubject());
        logger.info("exportToken method successfully worked");
        return claimFunction.apply(claims);
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();
    }

    public Map<String, String> getUserCredentials(String token) {

        Map<String, String> claims = Map.ofEntries(
                entry("userId", getClaims(token).get("userId").toString()),
                entry("name", getClaims(token).get("name").toString()),
                entry("surname", getClaims(token).get("surname").toString()),
                entry("companyId", getClaims(token).get("companyId").toString()),
                entry("companyName",getClaims(token).get("companyName").toString()),
                entry("companyFleetId", getClaims(token).get("companyFleetId").toString()),
                entry("companyFleetName", getClaims(token).get("companyFleetName").toString()),
                entry("role", getClaims(token).get("role").toString())
        );

        return claims;
    }

    private Key getKey() {
        logger.info("getKey method started");
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        logger.info("getKey method successfully worked");
        return Keys.hmacShaKeyFor(key);
    }


}
