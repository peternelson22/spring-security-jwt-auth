package com.nelson.demo.config;

import com.nelson.demo.model.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; //24 hours

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;


    public String generateToken(UserEntity user) {
        return Jwts.builder()
                .setSubject(String.format("%s, %s", user.getId(), user.getEmail()))
                .setIssuer("NELSON")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT Expired " + e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("Token is null or empty " + e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("JWT invalid " + e.getMessage());
        } catch (SignatureException e) {
            LOGGER.error("JWT failed");
        }
        return false;
    }

    public String getSubject(String token){
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
