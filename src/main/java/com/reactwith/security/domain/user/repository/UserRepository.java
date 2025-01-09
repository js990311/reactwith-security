package com.reactwith.security.domain.user.repository;

import com.reactwith.security.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
