package com.reactwith.security.domain.user.service;

import com.reactwith.security.domain.role.entity.Role;
import com.reactwith.security.domain.role.entity.RoleName;
import com.reactwith.security.domain.role.repository.RoleRepository;
import com.reactwith.security.domain.user.entity.SocialType;
import com.reactwith.security.domain.user.entity.User;
import com.reactwith.security.domain.user.entity.UserRole;
import com.reactwith.security.domain.user.repository.UserRepository;
import com.reactwith.security.domain.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserRegistService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Map<String, Object> registUser(String email, String password, SocialType social){
        if(userRepository.existsByEmailAndSocial(email, social.toString())){
            return Map.of("status", "you are aleady member");
        }
        Role role = roleRepository.findById(RoleName.USER.toString()).orElseThrow();
        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .social(social.toString())
                .nickname(email)
                .build();
        UserRole userRole = UserRole.builder()
                .user(user)
                .role(role)
                .build();
        user.addUserRole(userRole);
        userRepository.save(user);

        return Map.of("status", "Success Regist");
    }
}
