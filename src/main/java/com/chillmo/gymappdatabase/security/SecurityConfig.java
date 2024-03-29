package com.chillmo.gymappdatabase.security;

import com.chillmo.gymappdatabase.users.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserService myUserDetailsService;

    public SecurityConfig(UserService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/users/signup", "/api/users/verify","/api/signup/register","/api/healthdata/add","/api/**") // Disable CSRF protection for signup endpoint
                )
                .authorizeRequests(auth -> auth
                        .requestMatchers("/h2-console/**", "/api/users/signup", "/api/users/verify", "/api/users/all","/api/signup/register","/api/healthdata/add","/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(myUserDetailsService)
                .httpBasic(withDefaults());
        // Enable debug logging


        return http.build();


    }


}