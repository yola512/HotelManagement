package com.example.hotelmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // public endpoints (no authentication required)

                        .requestMatchers(HttpMethod.GET, "/api/meals/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/rooms/**").permitAll()
                        // admin-only endpoints
                        .requestMatchers(HttpMethod.POST, "/api/meals/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/meals/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/meals/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/bookings").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/bookings/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/rooms/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/rooms/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/rooms/**").hasRole("ADMIN")
                        .requestMatchers("/api/reports/**").hasRole("ADMIN")

                        // client-only endpoints
                        .requestMatchers(HttpMethod.GET, "/api/bookings/myBookings").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.POST, "/api/bookings").hasRole("CLIENT")
                        // review endpoints
                        .requestMatchers(HttpMethod.GET, "/api/reviews").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/reviews/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/reviews").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.DELETE, "/api/reviews/{id}").hasAnyRole("ADMIN", "CLIENT")
                        // default: all other endpoints require authentication
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withUsername("admin@hotel.com")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();

        UserDetails client = User.withUsername("client@hotel.com")
                .password("{noop}client123")
                .roles("CLIENT")
                .build();

        return new InMemoryUserDetailsManager(admin, client);
    }


}