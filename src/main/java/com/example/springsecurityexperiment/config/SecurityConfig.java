package com.example.springsecurityexperiment.config;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
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
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// Enable CORS and disable CSRF
		http = http.cors().and().csrf().disable();

		// Set session management to stateless
		http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

		// Set authorized requests exception handler
		http = http.exceptionHandling()
						.authenticationEntryPoint(((request, response, authException) -> {
							response.sendError(
											HttpServletResponse.SC_UNAUTHORIZED,
											authException.getMessage()
							);
						})).and();

		// Set permission on endpoints
		http.authorizeHttpRequests((auth) -> auth
						// Our public endpoints
						.requestMatchers("/public/**").permitAll()
						// Our private endpoints
						.anyRequest().authenticated());

		// Add JWT token filter
//		http.addFilterBefore(
//		)

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return source;
	}


	@Bean
	public UserDetailsService users() {
		UserDetails user = User.builder()
						.username("user")
						.password("{bcrypt}$2a$10$E2MXLbsCKbMjqfc1xZLthOM9HvtUJbMDyJDygTTssvO008fka1U2K")
						.roles("USER")
						.build();
		UserDetails admin = User.builder()
						.username("admin")
						.password("{bcrypt}$2a$10$E2MXLbsCKbMjqfc1xZLthOM9HvtUJbMDyJDygTTssvO008fka1U2K")
						.roles("USER", "ADMIN")
						.build();
		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
