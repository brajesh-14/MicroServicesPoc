package com.security.controller;

import com.security.dto.LoginRequest;
import com.security.dto.RegisterRequest;
import com.security.entity.User;
import com.security.enums.RoleType;
import com.security.repository.UserRepo;
import com.security.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){

        if(userRepo.existsByUsername(request.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        }

        RoleType role;
        try {
            role = RoleType.valueOf(request.getRole());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Invalid role");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepo.save(user);

        return ResponseEntity.ok("User registered successfully...");
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){

        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid Username or Password"));

       if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                   .body("Invalid Username or Password");
       }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

        return ResponseEntity.ok(Map.of("token", token));
    }
}
