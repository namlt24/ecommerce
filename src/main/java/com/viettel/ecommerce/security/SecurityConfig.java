package com.viettel.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder PasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/api/users/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/users").permitAll()// Cấp phép cho các đường dẫn này không cần xác thực
                        .anyRequest().authenticated()              // Tất cả các yêu cầu khác cần xác thực
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}
