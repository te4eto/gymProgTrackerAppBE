package com.example.service;


import com.example.auth.JwtService;
import com.example.model.dto.auth.AuthResponse;
import com.example.model.dto.auth.LoginRequest;
import com.example.model.dto.auth.RegisterRequest;
import com.example.model.entity.auth.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.username())) {
            throw new IllegalArgumentException("Username already taken");
        }
        User user = User.builder()
                .username(req.username())
                .password(passwordEncoder.encode(req.password()))
                .role("USER")
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user.getUsername(), user.getRole());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByUsername(req.username())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        String token = jwtService.generateToken(user.getUsername(), user.getRole());
        return new AuthResponse(token);
    }
}