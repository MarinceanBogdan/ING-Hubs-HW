package com.example.product_management.Controller;

import com.example.product_management.Dto.UserDto;
import com.example.product_management.Entity.User;
import com.example.product_management.Repository.UserRepository;
import com.example.product_management.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto){
        return null;

    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        User user = userRepository.findByEmail(email);

        if(user == null) {
//            throw new UserNotFoundException();
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashed = encoder.encode("password");

        if(user.getPassword().equals(hashed)) {
            String jwtToken = JwtUtil.generateToken(user.getEmail(), user.getRole().getRole().name());
            return ResponseEntity.ok(jwtToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}
