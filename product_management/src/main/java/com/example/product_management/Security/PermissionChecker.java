package com.example.product_management.Security;

import com.example.product_management.Entity.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class PermissionChecker {
    public boolean hasPermission(User user, String permissionName) {
        if (user == null || user.getRole() == null) return false;

        return user.getRole().getPermissions().stream()
                .anyMatch(p -> p.getPermissionName().equalsIgnoreCase(permissionName));
    }
}
