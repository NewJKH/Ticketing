package com.concert.ticketing.common.config;

import com.concert.ticketing.common.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)  // .csrf().disable() 방식은 더 이상 사용 안함.
                .httpBasic(AbstractHttpConfigurer::disable) // BasicAuthenticationFilter 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // UsernamePasswordAuthenticationFilter, DefaultLoginPageGeneratingFilter 비활성화
                .addFilterBefore(jwtFilter, SecurityContextHolderAwareRequestFilter.class)
                // 경로별 인증 정책 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/login",
                                "/api/signup",
                                "/api/concert",
                                "/api/seats"
                        ).permitAll() // 인증 제외 경로
                        .anyRequest().authenticated() // 나머지는 인증 필요
                )
                .build();
    }
}



