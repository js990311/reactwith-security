package com.reactwith.security.domain.user.service;

import com.reactwith.security.domain.role.entity.Role;
import com.reactwith.security.domain.role.entity.RoleName;
import com.reactwith.security.domain.role.repository.RoleRepository;
import com.reactwith.security.domain.user.entity.SocialType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRegistServiceTest {
    @Autowired
    private UserRegistService userRegistService;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void registUser() {
        roleRepository.save(Role.from(RoleName.USER));
        String email = "123@example.com";
        String password = "imstrongpassword";
        userRegistService.registUser(email, password, SocialType.Local);
    }
}