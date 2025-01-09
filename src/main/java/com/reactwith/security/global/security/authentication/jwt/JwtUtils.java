package com.reactwith.security.global.security.authentication.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class JwtUtils {
    private static String key = "04b1abc1cc3085d56414f0894a13feb7aaf109b0e0976a7ed8e18c3890727010174dd1628e33d90c33f8d5207202c6fd7663d9d307d3cfb994563419fa6cd5a80a5daa72cb91ad7ff7c6b1ec86714e9490a335297b456ed8048a07ebee0274a0c9144226f7a2168f39fc57b6ba14ca37ad28436c34844632241e67ef6104b27747bf033cab1bdf9f502e74fa1acadb4519edd90f88d84b297b83abe8dbfbefc0";

    public static String generateToken(Map<String, Object> claims, int min){
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        String token = Jwts.builder()
                .setHeader(Map.of("typ", "JWT"))
                .setClaims(claims)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(secretKey)
                .compact();
        return token;
    }

    public static Map<String, Object> validateToken(String token){
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
