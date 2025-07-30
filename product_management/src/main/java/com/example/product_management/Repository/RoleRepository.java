package com.example.product_management.Repository;

import com.example.product_management.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {


    Role findByName(String roleName);
}
