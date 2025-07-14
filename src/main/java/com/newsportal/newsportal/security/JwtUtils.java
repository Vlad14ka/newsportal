package com.newsportal.newsportal.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component // Указывает Spring, что это компонент, который можно внедрять
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // Секретный ключ для подписи JWT. Должен быть достаточно длинным и храниться в безопасности.
    // @Value("${jwt.secret}") - значение будет браться из application.properties
    @Value("${jwt.secret}")
    private String jwtSecret;

    // Время жизни JWT в миллисекундах
    @Value("${jwt.expiration.ms}")
    private int jwtExpirationMs;

    // Генерируем секретный ключ из строки
    private Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // Генерируем JWT токен для аутентифицированного пользователя
    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername())) // Устанавливаем имя пользователя как Subject токена
                .setIssuedAt(new Date()) // Время выдачи токена
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Время истечения токена
                .signWith(key(), SignatureAlgorithm.HS512) // Подписываем токен секретным ключом с алгоритмом HS512
                .compact(); // Строим и сжимаем токен в строку
    }

    // Извлекаем имя пользователя из JWT токена
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // Валидируем JWT токен
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}