package com.campusos.service;

import com.campusos.config.JwtService;
import com.campusos.entity.User;
import com.campusos.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtService jwtService,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // LOGIN
    // =========================
    public String login(String email, String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        if (!user.isActive()) {
            throw new RuntimeException("Account is inactive");
        }

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(email);

        return jwtService.generateToken(userDetails);
    }

    // =========================
    // REGISTER
    // =========================
    public User register(
            String name,
            String email,
            String password,
            String role
    ) {

        // Check duplicate email
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException(
                    "Email already registered"
            );
        }

        // Create user
        User user = new User();

        user.setName(name);
        user.setEmail(email);

        // IMPORTANT:
        // Never store plain password
        user.setPassword(
                passwordEncoder.encode(password)
        );

        user.setRole(role);
        user.setActive(true);

        return userRepository.save(user);
    }

    // =========================
    // GET USER
    // =========================
    public User getUser(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );
    }
}