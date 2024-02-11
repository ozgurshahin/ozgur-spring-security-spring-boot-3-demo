package org.example.ozgurspringsecurityspringboot3demo.service;

import lombok.RequiredArgsConstructor;
import org.example.ozgurspringsecurityspringboot3demo.entity.Role;
import org.example.ozgurspringsecurityspringboot3demo.entity.User;
import org.example.ozgurspringsecurityspringboot3demo.entity.UserRepository;
import org.example.ozgurspringsecurityspringboot3demo.model.LoginRequest;
import org.example.ozgurspringsecurityspringboot3demo.model.LoginResponse;
import org.example.ozgurspringsecurityspringboot3demo.security.JwtIssuer;
import org.example.ozgurspringsecurityspringboot3demo.security.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtIssuer jwtIssuer;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public LoginResponse loginUser(String email, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();
        var roles = principal.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .toList();
//        var token = jwtIssuer.issue(111L, request.getEmail(), List.of("USER"));
        var token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(), roles);
        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }

    public User createUser(LoginRequest request) {
        Set<Role> authorities = new HashSet<>();
        return userRepository.save(User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .authorities(authorities)
                .build());
    }
}
