package org.example.ozgurspringsecurityspringboot3demo.controller;

import lombok.RequiredArgsConstructor;
import org.example.ozgurspringsecurityspringboot3demo.entity.User;
import org.example.ozgurspringsecurityspringboot3demo.model.LoginRequest;
import org.example.ozgurspringsecurityspringboot3demo.model.LoginResponse;
import org.example.ozgurspringsecurityspringboot3demo.service.AuthenticationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        return authenticationService.loginUser(request.getEmail(), request.getPassword());
    }

    @PostMapping("/auth/crete-user")
    public User createUser(@RequestBody @Validated LoginRequest request) {
        return authenticationService.createUser(request);
    }
}
