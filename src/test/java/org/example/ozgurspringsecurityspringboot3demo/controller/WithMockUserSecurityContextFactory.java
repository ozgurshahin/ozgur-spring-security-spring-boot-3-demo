package org.example.ozgurspringsecurityspringboot3demo.controller;

import org.example.ozgurspringsecurityspringboot3demo.security.UserPrincipal;
import org.example.ozgurspringsecurityspringboot3demo.security.UserPrincipalAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockUser annotation) {
        var authorities = Arrays.stream(annotation.authorities())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        var principal = UserPrincipal.builder()
                .userId(annotation.userId())
                .email("fake@test.com")
                .authorities(authorities)
                .build();

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UserPrincipalAuthenticationToken(principal));
        return context;

    }
}
