package com.reactwith.security.domain.user.controller;

import com.reactwith.security.domain.user.entity.SocialType;
import com.reactwith.security.domain.user.form.UserRegistForm;
import com.reactwith.security.domain.user.service.UserRegistService;
import com.reactwith.security.global.security.authentication.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRegistService userRegistService;

    @PostMapping("/regist")
    public Map<String, Object> postUserReigst(
            @RequestBody UserRegistForm form
    ){
        return userRegistService.registUser(form.getEmail(), form.getPassword(), SocialType.Local);
    }

    @PostMapping("/refresh")
    public Map<String, Object> refresh(
            @RequestHeader("Authorization") String authorization
    ) throws AuthenticationException {
        if (authorization == null){
            throw new AuthenticationException();
        }
        if (!authorization.startsWith("Bearer ")){
            throw new AuthenticationException();
        }
        String token = authorization.substring(7);
        Map<String, Object> claims = JwtUtils.validateToken(token);
        claims.put("refresh", false);
        String accessToken = JwtUtils.generateToken(claims, 60);
        claims.put("refresh", true);
        String refreshToken = JwtUtils.generateToken(claims, 24*60*7);
        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);
        return claims;
    }
}
