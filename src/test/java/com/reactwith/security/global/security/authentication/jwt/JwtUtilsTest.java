package com.reactwith.security.global.security.authentication.jwt;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {
    private String key = "test";
    private String value = "token";

    @Test
    void testJwtsUtils() {
        String token = JwtUtils.generateToken(Map.of(key, value), 60);
        Map<String, Object> map = JwtUtils.validateToken(token);

        assertEquals(value, map.get(key));
    }
}