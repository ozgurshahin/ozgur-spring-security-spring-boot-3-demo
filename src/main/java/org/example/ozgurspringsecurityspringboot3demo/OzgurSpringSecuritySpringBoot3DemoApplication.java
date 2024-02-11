package org.example.ozgurspringsecurityspringboot3demo;

import org.example.ozgurspringsecurityspringboot3demo.entity.Role;
import org.example.ozgurspringsecurityspringboot3demo.entity.RoleRepository;
import org.example.ozgurspringsecurityspringboot3demo.entity.User;
import org.example.ozgurspringsecurityspringboot3demo.entity.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class OzgurSpringSecuritySpringBoot3DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OzgurSpringSecuritySpringBoot3DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode) {
        return args -> {
            if (roleRepository.findByAuthority("ROLE_ADMIN").isPresent()) return;
            Role adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
            Role userRole = roleRepository.save(new Role("ROLE_USER"));

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);


            userRepository.save(User.builder()
                    .id(1L)
                    .email("admin@test.com")
                    .password(passwordEncode.encode("password"))
                    .authorities(adminRoles)
                    .extraInfo(null)
                    .build());

            userRepository.save(User.builder()
                    .id(2L)
                    .email("user@test.com")
                    .password(passwordEncode.encode("password"))
                    .authorities(userRoles)
                    .extraInfo(null)
                    .build());
        };
    }
}
