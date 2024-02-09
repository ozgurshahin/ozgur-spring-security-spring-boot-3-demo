package org.example.ozgurspringsecurityspringboot3demo.controller;

import lombok.RequiredArgsConstructor;
import org.example.ozgurspringsecurityspringboot3demo.model.LoginRequest;
import org.example.ozgurspringsecurityspringboot3demo.model.LoginResponse;
import org.example.ozgurspringsecurityspringboot3demo.service.AuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        return authService.attemptLogin(request.getEmail(), request.getPassword());
    }
}
