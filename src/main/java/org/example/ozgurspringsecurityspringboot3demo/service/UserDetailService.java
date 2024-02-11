package org.example.ozgurspringsecurityspringboot3demo.service;

import lombok.RequiredArgsConstructor;
import org.example.ozgurspringsecurityspringboot3demo.entity.UserRepository;
import org.example.ozgurspringsecurityspringboot3demo.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {//UserDetails in the UserPrincipal the same
        var user = userRepository.findByEmail(username).orElseThrow();
        return UserPrincipal.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .authorities(user.getAuthorities())
                .password(user.getPassword())
                .build();
    }
}
