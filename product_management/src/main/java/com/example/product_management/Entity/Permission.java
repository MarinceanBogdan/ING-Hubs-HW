package com.example.product_management.Entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Getter;

@Entity
@Table(name = "permissions")
@Getter
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String permissionName;
}
