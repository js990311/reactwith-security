package com.reactwith.security.global.security.authentication.filter;

import com.reactwith.security.global.security.authentication.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.List;
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
        authenticationByToken(request);
        filterChain.doFilter(request,response);
    }

    protected void authenticationByToken(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return;
        }
        String token = authorization.substring(7);
        Map<String, Object> claims = JwtUtils.validateToken(token);
        List<String> roles = (List<String>) claims.get("roles");
        
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            claims, null, roles.stream().map(role->new SimpleGrantedAuthority(role)).toList()
        );

        SecurityContext context = new SecurityContextImpl(authentication);
        SecurityContextHolder.getContextHolderStrategy().setContext(context);
    }
}
