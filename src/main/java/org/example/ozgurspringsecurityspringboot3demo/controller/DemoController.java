package org.example.ozgurspringsecurityspringboot3demo.controller;

import lombok.RequiredArgsConstructor;
import org.example.ozgurspringsecurityspringboot3demo.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DemoController {
    @GetMapping("/")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal UserPrincipal principal) {
        return "it means you log in as secured user with email: " + principal.getEmail() + " and id: " + principal.getUserId() + " and authorities: " + principal.getAuthorities();
    }

    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal UserPrincipal principal) {
        return "it means you log in as admin with email: " + principal.getEmail() + " and id: " + principal.getUserId() + " and authorities: " + principal.getAuthorities();
    }
}
