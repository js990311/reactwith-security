package com.reactwith.security.domain.user.entity;

import com.reactwith.security.domain.role.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_role_id")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "user_id", updatable = false, insertable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    @Column(name = "role_name", updatable = false, insertable = false)
    private String roleName;
}
