package com.example.springsecurityexperiment.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public JwtTokenFilter(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
       if (isEmpty(header) || !header.startsWith("Basic ")) {
           filterChain.doFilter(request, response);
           return;
       }
       final String token = header.split(" ")[1].trim();
       String decoded = new String(Base64.getDecoder().decode(token));
       List<String> decodedValues = List.of(decoded.split(":"));
       String username = decodedValues.get(0);
       String password = decodedValues.get(1);
       UserDetails userDetails = userDetailsService.loadUserByUsername(username);
       if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
           filterChain.doFilter(request, response);
           return;
       }
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    // extends OncePerRequestFilter
}
