package com.example.product_management.Entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "roles")
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions", // better name
            joinColumns = @JoinColumn(name = "role_id"), // this entity's ID
            inverseJoinColumns = @JoinColumn(name = "permission_id") // target entity's ID
    )
    private List<Permission> permissions;
    
    public enum RoleName {
        CUSTOMER,
        EMPLOYEE,
        ADMIN
    }
}
