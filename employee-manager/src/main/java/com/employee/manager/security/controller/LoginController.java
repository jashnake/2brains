package com.employee.manager.security.controller;

import com.employee.manager.security.config.service.UserService;
import com.employee.manager.security.model.JwtResponse;
import com.employee.manager.security.model.LoginRequest;
import com.employee.manager.security.model.RegisterRequest;
import com.employee.manager.security.utils.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");

        userService.createUser(
                registerRequest.getUsername(),
                registerRequest.getPassword(),
                roles
        );

        return ResponseEntity.ok(("User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        String jwt = jwtUtils.generateJwtToken(loginRequest.getUsername());

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                loginRequest.getUsername()
        ));
    }
}