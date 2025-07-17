package com.newsportal.newsportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentRequest {
    @NotBlank
    @Size(min = 1, max = 500)
    private String text;

    // Геттеры и Сеттеры
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}