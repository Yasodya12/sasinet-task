package com.sasinet.sasinetTask.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component

public class JwtAuthorizationFilter  extends OncePerRequestFilter{


    private final JwtUtil jwtUtil;
    private final ObjectMapper mapper;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, ObjectMapper mapper) {
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
    }

//    This function intercepts incoming HTTP requests to extract and validate JWT tokens.
//     If the token is valid, it sets the user's authentication in the SecurityContext.
//     Handles errors gracefully by responding with appropriate error details.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // A map to store error details in case of exceptions
        Map<String, Object> errorDetails = new HashMap<>();
        System.out.println("Filtering internal start");

        try {
            // Retrieve the JWT token from the request header
            String accessToken = jwtUtil.resolveToken(request);

            // If the token is not present, allow the request to proceed without authentication
            if (accessToken == null) {
                System.out.println("token is null");
                filterChain.doFilter(request, response);
                return;
            }

            System.out.println("token : " + accessToken);

            // Extract claims from the token
            Claims claims = jwtUtil.resolveClaims(request);

            // If claims are valid, proceed with authentication
            if (claims != null && jwtUtil.validateClaims(claims)) {
                // Extract the subject (email) from the claims
                String email = claims.getSubject();

                // Extract roles from the claims
                List<String> roles = jwtUtil.getRoles(claims);
                System.out.println("roles: " + roles);

                // Convert roles to Spring Security authorities
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(e -> new SimpleGrantedAuthority(e))
                        .toList();

                // Create an authentication object
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(email, "", authorities);

                // Set the authentication object in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("authenticated");
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the authentication process
            System.out.println(e.getMessage());

            // Prepare error details to be sent in the response
            errorDetails.put("message", "Authentication Error");
            errorDetails.put("details", e.getMessage());

            // Set the HTTP response status and content type
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            // Write the error details to the response body
            mapper.writeValue(response.getWriter(), errorDetails);
            response.getWriter().flush();

            // Exit the filter chain after handling the exception
            return;
        }

        // Proceed with the filter chain for the next filter or endpoint
        filterChain.doFilter(request, response);
    }
}