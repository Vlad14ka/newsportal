package com.newsportal.newsportal.dto;

import jakarta.validation.constraints.NotBlank; // Для валидации

public class LoginRequest {

    @NotBlank // Поле не должно быть пустым
    private String username;

    @NotBlank // Поле не должно быть пустым
    private String password;

    // Конструктор по умолчанию (требуется для десериализации JSON)
    public LoginRequest() {
    }

    // Конструктор с параметрами
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Геттеры и Сеттеры (можно сгенерировать через IDE, если нет Lombok)
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
}