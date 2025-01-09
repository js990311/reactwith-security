package com.reactwith.security.domain.role.entity;

import com.reactwith.security.domain.user.entity.UserRole;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany
    private List<UserRole> userRole;
}
