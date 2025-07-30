package com.example.product_management.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions", // better name
            joinColumns = @JoinColumn(name = "role_id"), // this entity's ID
            inverseJoinColumns = @JoinColumn(name = "permission_id") // target entity's ID
    )
    private List<Permission> permissions;

    public Role(String role) {
        this.name = role;
    }

    public enum RoleName {
        CUSTOMER,
        EMPLOYEE,
        ADMIN
    }
}
