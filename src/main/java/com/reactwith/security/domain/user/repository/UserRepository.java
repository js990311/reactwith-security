package com.reactwith.security.domain.user.repository;

import com.reactwith.security.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u join fetch u.userRoles where u.email = :username")
    Optional<User> findByUsername(@Param("username") String username);

    boolean existsByEmailAndSocial(@Param("email") String email, @Param("social") String social);
}
