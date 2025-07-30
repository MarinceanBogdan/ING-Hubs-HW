package com.example.product_management.Entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String first_name;

    private String last_name;

    private String email;

    private String phone_number;

    private String password;

    private LocalDateTime created_at = LocalDateTime.now();

    private LocalDateTime updated_at = LocalDateTime.now();

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
