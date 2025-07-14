package com.newsportal.newsportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import com.newsportal.newsportal.security.JwtAuthFilter;
import com.newsportal.newsportal.security.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration // Указывает Spring, что это класс конфигурации
@EnableWebSecurity // Включает веб-безопасность Spring Security
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    // Определяем PasswordEncoder для хеширования паролей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Получаем AuthenticationManager из AuthenticationConfiguration.
// Он будет использоваться для аутентификации пользователя (например, при логине)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Определяем провайдер аутентификации, который использует наш UserDetailsService
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Указываем наш UserDetailsService
        authProvider.setPasswordEncoder(passwordEncoder()); // Указываем наш PasswordEncoder
        return authProvider;
    }

    // Конфигурация цепочки фильтров безопасности HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/news/**").permitAll() // Разрешаем всем доступ к новостям
                        .requestMatchers("/api/comments/**").permitAll() // Разрешаем всем доступ к комментариям
                        .requestMatchers("/api/auth/**").permitAll() // Разрешаем доступ к эндпоинтам аутентификации (регистрация, логин)
                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // Только для ролей ADMIN
                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                );

        // Добавляем наш AuthenticationProvider
        http.authenticationProvider(authenticationProvider());

        // Добавляем наш JWT фильтр перед фильтром UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // Настройка для H2-консоли
        http.headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.disable())
        );

        return http.build();
    }
}