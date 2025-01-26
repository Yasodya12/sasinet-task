package com.sasinet.sasinetTask.configuration;

import com.sasinet.sasinetTask.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@CrossOrigin
public class SecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final UserService adminService;

    public SecurityConfig(UserService adminService, JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.adminService = adminService;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;

    }

    // Configures the security filter chain for handling requests and JWT authorization.

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("SecurityFilterChain");

        // Disable CSRF and CORS as part of configuration
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        System.out.println("before authorization configuration");

        http
                // Define authorization rules for incoming requests
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers("/users/login/**").permitAll() // Allow login endpoints
                            .requestMatchers("/users/register/**").permitAll() // Allow registration endpoints
                            .requestMatchers("/api/**").hasAnyAuthority("USER") // Restrict /api/** to "USER" authority
                            .anyRequest().authenticated(); // Require authentication for all other requests
                })
                // Add JWT authorization filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        System.out.println("it returned");

        // Build and return the security filter chain
        return http.build();



    }


}
