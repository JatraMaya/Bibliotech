package com.jatramaya.bibliotech.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jatramaya.bibliotech.entity.user.Role;
import com.jatramaya.bibliotech.entity.user.UserEntity;
import com.jatramaya.bibliotech.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Value("${ADMIN_PASSWORD}")
    private String password;

    @Bean
    CommandLineRunner initAdminUser(UserRepository repo, PasswordEncoder passEncoder) {
        return args -> {
            if (!repo.existsByUsername("admin")) {
                UserEntity admin = new UserEntity();

                admin.setUsername("admin");
                admin.setFirstname("admin");
                admin.setEmail("admin.example.com");
                admin.setRole(Role.ADMIN);
                admin.setPassword(passEncoder.encode(password));

                repo.save(admin);

                System.out.println("ADMIN USER Crated");

            }
        };
    }

}
