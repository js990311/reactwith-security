package com.reactwith.security.domain.user.dto;

import com.reactwith.security.domain.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserToken extends User {
    private String email;
    private String password;
    private String nickname;
    private String social;
    private List<String> roles;

    public Map<String, Object> getClaims(){
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", email);
        claims.put("nickname", nickname);
        claims.put("social", social);
        claims.put("roles", roles);
        return claims;
    }

    public UserToken(String email, String password, String nickname, String social, List<String> roles) {
        super(email, password, roles.stream().map(role->new SimpleGrantedAuthority("ROLE_"+role)).toList());
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.social = social;
        this.roles = roles;
    }

    public static UserToken of(com.reactwith.security.domain.user.entity.User user){
        return new UserTokenBuilder()
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .social(user.getNickname())
                .roles(user.getUserRoles().stream().map(UserRole::getRoleName).toList())
                .build();
    }

    public static class UserTokenBuilder{
        private String email;
        private String password;
        private String nickname;
        private String social;
        private List<String> roles;

        public UserToken build(){
            return new UserToken(
                    email,
                    password,
                    nickname,
                    social,
                    roles
            );
        }

        public UserTokenBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserTokenBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserTokenBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserTokenBuilder social(String social) {
            this.social = social;
            return this;
        }

        public UserTokenBuilder roles(List<String> roles) {
            this.roles = roles;
            return this;
        }
    }
}
