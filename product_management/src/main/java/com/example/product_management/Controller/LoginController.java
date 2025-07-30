package com.example.product_management.Controller;

import com.example.product_management.Entity.Role;
import com.example.product_management.Entity.User;
import com.example.product_management.Repository.RoleRepository;
import com.example.product_management.Repository.UserRepository;
import com.example.product_management.Security.PermissionChecker;
import com.example.product_management.Util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLDataException;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionChecker permissionChecker;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@RequestBody User user) throws DataException {

        try {
            User newUser = (User) userRepository.findByEmail(user.getEmail()).orElse(null);
            if(newUser != null) {
                throw new DataException("User already exists exists!", new SQLDataException());
            }

            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode(user.getPassword());
            Role role = roleRepository.findByName(user.getRole().getName());
            newUser = new User(user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPhone_number(), hashedPassword, role);
            userRepository.save(newUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body("User created successfully!");

    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        try {
        User user = (User) userRepository.findByEmail(email).orElse(null);

        if(user == null) {
            throw new Exception("User not found");
        }

            PasswordEncoder encoder = new BCryptPasswordEncoder();

            if (encoder.matches(password, user.getPassword())) {
                String jwtToken = JwtUtil.generateToken(user.getEmail());
                return ResponseEntity.ok(jwtToken);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e){
                throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/getUser/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable String email, HttpServletRequest request) {
        User user = (User) userRepository.findByEmail(email).orElse(null);

        User requester = (User) request.getAttribute("user");
        if (requester == null || !permissionChecker.hasPermission(requester, "READ")) {
            return ResponseEntity.status(403).body(null);
        }

        return ResponseEntity.ok().body(user);
    }

}
