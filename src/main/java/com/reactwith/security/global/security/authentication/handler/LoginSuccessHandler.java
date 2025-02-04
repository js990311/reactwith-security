package com.reactwith.security.global.security.authentication.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.reactwith.security.domain.user.dto.UserToken;
import com.reactwith.security.global.security.authentication.jwt.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserToken token = (UserToken) authentication.getPrincipal();

        Gson gson = new Gson();
        Map<String, Object> claims = token.getClaims();
        claims.put("refresh", false);
        String accessToken = JwtUtils.generateToken(claims, 60);
        claims.put("refresh", true);
        String refreshToken = JwtUtils.generateToken(claims, 60*24*7);
        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);

        response.setContentType("application/json; charset=UTF-8");

        PrintWriter printWriter = response.getWriter();
        printWriter.println(gson.toJson(claims));
        printWriter.close();
    }
}
