package com.reactwith.security.domain.role.entity;

import com.reactwith.security.domain.user.entity.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "role_name")
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "role")
    private List<UserRole> userRole = new ArrayList<>();

    public static Role from(RoleName roleName){
        return Role.builder()
                .name(roleName.toString())
                .build();
    }
}
