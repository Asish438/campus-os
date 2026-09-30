package com.campusos.config;

import com.campusos.entity.User;
import com.campusos.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {

        return args -> {

            createUser(
                    userRepository,
                    passwordEncoder,
                    "Demo Student",
                    "student@campusos.com",
                    "student123",
                    "STUDENT"
            );

            createUser(
                    userRepository,
                    passwordEncoder,
                    "Demo Faculty",
                    "faculty@campusos.com",
                    "faculty123",
                    "FACULTY"
            );

            createUser(
                    userRepository,
                    passwordEncoder,
                    "Demo Warden",
                    "warden@campusos.com",
                    "warden123",
                    "WARDEN"
            );

            createUser(
                    userRepository,
                    passwordEncoder,
                    "Demo Security",
                    "security@campusos.com",
                    "security123",
                    "SECURITY"
            );

            createUser(
                    userRepository,
                    passwordEncoder,
                    "Demo Admin",
                    "admin@campusos.com",
                    "admin123",
                    "ADMIN"
            );

            createUser(
                    userRepository,
                    passwordEncoder,
                    "Demo Accounts",
                    "accounts@campusos.com",
                    "accounts123",
                    "ACCOUNTS"
            );

            createUser(
                    userRepository,
                    passwordEncoder,
                    "Demo Transport",
                    "transport@campusos.com",
                    "transport123",
                    "TRANSPORT"
            );
        };
    }

    private void createUser(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            String name,
            String email,
            String password,
            String role
    ) {

        if (!userRepository.existsByEmail(email)) {

            User user = new User();

            user.setName(name);

            user.setEmail(email);

            user.setPassword(
                    passwordEncoder.encode(password)
            );

            user.setRole(role);

            user.setActive(true);

            userRepository.save(user);
        }
    }
}