package com.example.springsecurityexperiment.config.securityconfig;


import com.example.springsecurityexperiment.config.JwtTokenFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@DependsOn({"userDetailsService", "corsConfigurationSource"})
public class JwtAuthSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    public JwtAuthSecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {

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
        http.securityMatcher("/jwt/**").authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        // Add JWT token filter
        http.addFilterBefore(
				this.jwtTokenFilter,
				UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

}
