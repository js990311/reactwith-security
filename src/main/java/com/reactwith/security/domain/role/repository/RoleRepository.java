package com.reactwith.security.domain.role.repository;

import com.reactwith.security.domain.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
