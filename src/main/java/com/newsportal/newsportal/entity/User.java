package com.newsportal.newsportal.entity;

import jakarta.persistence.*;
import lombok.Data; // Используем Lombok @Data
import lombok.NoArgsConstructor; // Используем Lombok @NoArgsConstructor

@Entity // Указывает, что это JPA сущность
@Table(name = "users") // Имя таблицы в базе данных (избегаем конфликтов со словом 'user' в некоторых БД)
public class User {

    @Id // Помечает поле как первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Стратегия автогенерации ID
    private Long id;

    @Column(nullable = false, unique = true, length = 50) // Логин пользователя, обязателен и уникален
    private String username;

    @Column(nullable = false) // Хешированный пароль, обязателен
    private String password;

    @Column(nullable = false, unique = true, length = 100) // Email, обязателен и уникален
    private String email;

    // Роль пользователя: ADMIN, USER.
    // @Enumerated(EnumType.STRING) сохраняет Enum как строку в БД.
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    // Конструктор для создания нового пользователя (без ID, он генерируется БД)
    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}