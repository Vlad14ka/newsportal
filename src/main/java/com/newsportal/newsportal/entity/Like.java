package com.newsportal.newsportal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "news_id"}) // Убеждаемся, что один пользователь может поставить только один лайк на новость
})
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Кто поставил лайк

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    private News news; // Какой новости поставили лайк

    @Column(nullable = false)
    private LocalDateTime likeDate; // Дата и время лайка

    // Конструктор по умолчанию
    public Like() {
        this.likeDate = LocalDateTime.now();
    }

    // Конструктор с параметрами
    public Like(User user, News news) {
        this.user = user;
        this.news = news;
        this.likeDate = LocalDateTime.now();
    }

    // --- Геттеры и Сеттеры (сгенерируй через IDE) ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public LocalDateTime getLikeDate() {
        return likeDate;
    }

    public void setLikeDate(LocalDateTime likeDate) {
        this.likeDate = likeDate;
    }
}