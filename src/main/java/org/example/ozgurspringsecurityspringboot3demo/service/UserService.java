package org.example.ozgurspringsecurityspringboot3demo.service;

import lombok.RequiredArgsConstructor;
import org.example.ozgurspringsecurityspringboot3demo.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    //In real here must be a repository
    private static final String EXISTING_EMAIL = "test@test.com";
    private static final String ANOTHER_EMAIL = "next@test.com";

    public Optional<UserEntity> findByEmail(String email) {
        if (EXISTING_EMAIL.equalsIgnoreCase(email)) {
            var user = new UserEntity();
            user.setId(111L);
            user.setEmail(EXISTING_EMAIL);
            user.setPassword("$2a$12$vh8M9IGHRlMbBSeJzFKfSOdCzN6JCFNg3f2mczgH5B0X/GMX1Vg6."); //password is "test" created by BCryptPasswordEncoder https://bcrypt-generator.com/
            user.setRole("ROLE_ADMIN");
            user.setExtraInfo("extra info");
            return Optional.of(user);
        } else if (ANOTHER_EMAIL.equalsIgnoreCase(email)) {
            var user = new UserEntity();
            user.setId(112L);
            user.setEmail(ANOTHER_EMAIL);
            user.setPassword("$2a$12$vh8M9IGHRlMbBSeJzFKfSOdCzN6JCFNg3f2mczgH5B0X/GMX1Vg6."); //password is "test" created by BCryptPasswordEncoder https://bcrypt-generator.com/
            user.setRole("ROLE_USER");
            user.setExtraInfo("extra info 2");
            return Optional.of(user);
        }

        return Optional.empty();
    }
}
