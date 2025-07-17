package com.newsportal.newsportal.dto;

import com.newsportal.newsportal.entity.Comment;
import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private String text;
    private LocalDateTime commentDate;
    private String authorUsername; // Имя автора комментария

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.commentDate = comment.getCommentDate();
        this.authorUsername = comment.getAuthor().getUsername();
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }
}