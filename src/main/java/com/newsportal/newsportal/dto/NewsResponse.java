package com.newsportal.newsportal.dto;

import com.newsportal.newsportal.entity.News;
import java.time.LocalDateTime;

public class NewsResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime publishDate;
    private String authorUsername; // Имя автора, а не весь объект User
    private long likesCount; // Количество лайков
    private long commentsCount; // Количество комментариев

    public NewsResponse(News news, long likesCount, long commentsCount) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.content = news.getContent();
        this.publishDate = news.getPublishDate();
        this.authorUsername = news.getAuthor().getUsername();
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
    }

    // Геттеры (сеттеры не нужны, так как это объект для ответа)
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public long getCommentsCount() {
        return commentsCount;
    }
}