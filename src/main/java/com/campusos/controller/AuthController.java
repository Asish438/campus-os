package com.campusos.controller;

import com.campusos.entity.User;
import com.campusos.service.AuthService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // =====================================================
    // LOGIN
    // =====================================================

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request
    ) {

        try {

            String token = authService.login(
                    request.getEmail(),
                    request.getPassword()
            );

            User user =
                    authService.getUser(request.getEmail());

            return ResponseEntity.ok(
                    new LoginResponse(
                            "Login successful",
                            token,
                            user.getId(),
                            user.getName(),
                            user.getEmail(),
                            user.getRole()
                    )
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(401)
                    .body(
                            new ErrorResponse(
                                    "Invalid email or password"
                            )
                    );
        }
    }

    // =====================================================
    // REGISTER
    // =====================================================

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        try {

            User user = authService.register(
                    request.getName(),
                    request.getEmail(),
                    request.getPassword(),
                    request.getRole()
            );

            return ResponseEntity.ok(
                    new RegisterResponse(
                            "Registration successful",
                            user.getId(),
                            user.getName(),
                            user.getEmail(),
                            user.getRole()
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            new ErrorResponse(
                                    e.getMessage()
                            )
                    );
        }
    }

    // =====================================================
    // LOGIN REQUEST
    // =====================================================

    public static class LoginRequest {

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // =====================================================
    // REGISTER REQUEST
    // =====================================================

    public static class RegisterRequest {

        @NotBlank
        private String name;

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String password;

        @NotBlank
        private String role;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    // =====================================================
    // LOGIN RESPONSE
    // =====================================================

    public static class LoginResponse {

        private String message;
        private String token;
        private Long userId;
        private String name;
        private String email;
        private String role;

        public LoginResponse(
                String message,
                String token,
                Long userId,
                String name,
                String email,
                String role
        ) {

            this.message = message;
            this.token = token;
            this.userId = userId;
            this.name = name;
            this.email = email;
            this.role = role;
        }

        public String getMessage() {
            return message;
        }

        public String getToken() {
            return token;
        }

        public Long getUserId() {
            return userId;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getRole() {
            return role;
        }
    }

    // =====================================================
    // REGISTER RESPONSE
    // =====================================================

    public static class RegisterResponse {

        private String message;
        private Long userId;
        private String name;
        private String email;
        private String role;

        public RegisterResponse(
                String message,
                Long userId,
                String name,
                String email,
                String role
        ) {

            this.message = message;
            this.userId = userId;
            this.name = name;
            this.email = email;
            this.role = role;
        }

        public String getMessage() {
            return message;
        }

        public Long getUserId() {
            return userId;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getRole() {
            return role;
        }
    }

    // =====================================================
    // ERROR RESPONSE
    // =====================================================

    public static class ErrorResponse {

        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}