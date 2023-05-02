package com.example.springsecurityexperiment.config.securityconfig;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// Configure AuthenticationManager that uses userDetailsService
// Config PasswordEncoder
// Configure HttpSecurity and adding custom filter
// Configure CorsFilter
@Configuration
@EnableWebSecurity
@DependsOn({"userDetailsService", "corsConfigurationSource"})
public class BasicAuthSecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain basicFilterChain(HttpSecurity http) throws Exception {

        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        // Set authorized requests exception handler
        http = http.exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) -> response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        authException.getMessage()
                ))).and();
        // Set permission on endpoints
        http.securityMatcher("/basic/**").authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/basic/admin/**").hasRole("ADMIN")
        ).httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
