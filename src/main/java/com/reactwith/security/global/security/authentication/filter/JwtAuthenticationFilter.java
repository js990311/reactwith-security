package com.reactwith.security.global.security.authentication.filter;

import com.reactwith.security.global.security.authentication.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        if (path.equals("/login")){
            return true; // should not check;
            // why function name is some...
        }
        return false; // should check
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if (authorization == null){
            throw new AuthenticationException();
        }
        if (!authorization.startsWith("Bearer ")){
            throw new AuthenticationException();
        }
        String substring = authorization.substring(7);
        Map<String, Object> claims = JwtUtils.validateToken(substring);

        filterChain.doFilter(request,response);

    }
}
