package com.newsportal.newsportal.controller;

import com.newsportal.newsportal.dto.NewsRequest;
import com.newsportal.newsportal.dto.NewsResponse;
import com.newsportal.newsportal.entity.News;
import com.newsportal.newsportal.service.CommentService;
import com.newsportal.newsportal.service.LikeService;
import com.newsportal.newsportal.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news") // Доступен для всех (гостей, юзеров, админов)
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private LikeService likeService; // Для подсчета лайков

    @Autowired
    private CommentService commentService; // Для подсчета комментариев

    // Получить все новости (доступно всем)
    @GetMapping
    public List<NewsResponse> getAllNews() {
        List<News> newsList = newsService.getAllNews();
        return newsList.stream()
                .map(news -> new NewsResponse(news, likeService.getLikesCountForNews(news.getId()),
                        commentService.getCommentsByNewsId(news.getId()).size()))
                .collect(Collectors.toList());
    }

    // Получить новость по ID (доступно всем)
    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> getNewsById(@PathVariable Long id) {
        return newsService.getNewsById(id)
                .map(news -> new NewsResponse(news, likeService.getLikesCountForNews(news.getId()),
                        commentService.getCommentsByNewsId(news.getId()).size()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- Эндпоинты для ADMIN ---
    // Создать новую новость (только для ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNews(@Valid @RequestBody NewsRequest newsRequest) {
        // Получаем имя текущего аутентифицированного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // Логин текущего пользователя

        try {
            News newNews = newsService.createNews(newsRequest.getTitle(), newsRequest.getContent(), currentUsername);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new NewsResponse(newNews, likeService.getLikesCountForNews(newNews.getId()),
                            commentService.getCommentsByNewsId(newNews.getId()).size())
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Обновить новость (только для ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNews(@PathVariable Long id, @Valid @RequestBody NewsRequest newsRequest) {
        try {
            News updatedNews = newsService.updateNews(id, newsRequest.getTitle(), newsRequest.getContent());
            return ResponseEntity.ok(
                    new NewsResponse(updatedNews, likeService.getLikesCountForNews(updatedNews.getId()),
                            commentService.getCommentsByNewsId(updatedNews.getId()).size())
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Или badRequest, если ошибка не в NotFound
        }
    }

    // Удалить новость (только для ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        try {
            newsService.deleteNews(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}