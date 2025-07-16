package com.newsportal.newsportal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT") // TEXT для длинного текста
    private String content;

    @Column(nullable = false)
    private LocalDateTime publishDate; // Дата и время публикации

    // Связь ManyToOne с User (автором новости)
    @ManyToOne(fetch = FetchType.LAZY) // LAZY - загружает автора только при обращении к нему
    @JoinColumn(name = "author_id", nullable = false) // Внешний ключ в таблице news
    private User author;

    // Связь OneToMany с Comment
    // orphanRemoval = true - удаление комментариев при удалении новости
    // mappedBy указывает на поле 'news' в сущности Comment
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // Связь OneToMany с Like
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    // Конструктор по умолчанию
    public News() {
        this.publishDate = LocalDateTime.now(); // Устанавливаем текущую дату при создании
    }

    // Конструктор с параметрами
    public News(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.publishDate = LocalDateTime.now();
    }

    // --- Геттеры и Сеттеры (сгенерируй через IDE: Alt+Insert -> Getter and Setter) ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }
}