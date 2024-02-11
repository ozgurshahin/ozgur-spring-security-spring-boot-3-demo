package org.example.ozgurspringsecurityspringboot3demo.security;

import lombok.RequiredArgsConstructor;
import org.example.ozgurspringsecurityspringboot3demo.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailService userDetailService;
    private final UnauthorisedHandler unauthorisedHandler;

    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(h -> h.authenticationEntryPoint(unauthorisedHandler))
                .securityMatcher("/**") //whole configuration is applied to all requests
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/").permitAll()// root path is permitted to everyone
                        .requestMatchers("/auth/login").permitAll() // login path is permitted to everyone
                        .requestMatchers("/auth/crete-user").permitAll() // login path is permitted to everyone
                        .requestMatchers("/admin/**").hasRole("ADMIN") // admin path is permitted to only users with role ADMIN
                        .requestMatchers("/secured/**").hasRole("USER") // admin path is permitted to only users with role ADMIN
                        .anyRequest().authenticated() // all other requests are authenticated
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }
}
