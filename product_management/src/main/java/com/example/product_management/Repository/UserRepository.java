package com.example.product_management.Repository;

import com.example.product_management.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {

    User findByEmail(String email);
}
