package com.newsportal.newsportal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(nullable = false)
    private LocalDateTime commentDate; // Дата и время комментария

    // Связь ManyToOne с User (автором комментария)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    // Связь ManyToOne с News (к какой новости относится комментарий)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    private News news;

    // Конструктор по умолчанию
    public Comment() {
        this.commentDate = LocalDateTime.now();
    }

    // Конструктор с параметрами
    public Comment(String text, User author, News news) {
        this.text = text;
        this.author = author;
        this.news = news;
        this.commentDate = LocalDateTime.now();
    }

    // --- Геттеры и Сеттеры (сгенерируй через IDE) ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}