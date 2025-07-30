package com.example.product_management.Entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;

@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String permissionName;
}
