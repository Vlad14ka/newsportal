package com.newsportal.newsportal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 50) // Длина имени пользователя
    private String username;

    @NotBlank
    @Size(max = 100)
    @Email // Валидация формата email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40) // Длина пароля
    private String password;

    // Конструктор по умолчанию
    public RegisterRequest() {
    }

    // Конструктор с параметрами
    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Геттеры и Сеттеры
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void voidsetPassword(String password) {
        this.password = password;
    }
}