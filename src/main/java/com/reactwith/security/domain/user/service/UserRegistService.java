package com.reactwith.security.domain.user.service;

import com.reactwith.security.domain.role.entity.Role;
import com.reactwith.security.domain.role.entity.RoleName;
import com.reactwith.security.domain.role.repository.RoleRepository;
import com.reactwith.security.domain.user.entity.User;
import com.reactwith.security.domain.user.entity.UserRole;
import com.reactwith.security.domain.user.repository.UserRepository;
import com.reactwith.security.domain.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserRegistService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registUser(String email, String password){
        Role role = roleRepository.findById(RoleName.User.toString()).orElseThrow();
        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .nickname(email)
                .build();
        UserRole userRole = UserRole.builder()
                .user(user)
                .role(role)
                .build();
        user.addUserRole(userRole);
        userRepository.save(user);
    }
}
