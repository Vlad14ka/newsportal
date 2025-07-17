package com.newsportal.newsportal.controller;

import com.newsportal.newsportal.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    // Получить количество лайков для новости (доступно всем)
    @GetMapping("/news/{newsId}/count")
    public ResponseEntity<Long> getLikesCountForNews(@PathVariable Long newsId) {
        try {
            long count = likeService.getLikesCountForNews(newsId);
            return ResponseEntity.ok(count);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Поставить лайк на новость (только для аутентифицированных пользователей)
    @PostMapping("/news/{newsId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addLikeToNews(@PathVariable Long newsId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        try {
            likeService.addLike(newsId, currentUsername);
            return ResponseEntity.status(HttpStatus.CREATED).body("Like added successfully!");
        } catch (RuntimeException e) {
            // Если пользователь уже поставил лайк или новость не найдена
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Удалить лайк с новости (только для аутентифицированных пользователей, которые его поставили)
    @DeleteMapping("/news/{newsId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> removeLikeFromNews(@PathVariable Long newsId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        try {
            likeService.removeLike(newsId, currentUsername);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            // Если лайк не найден или пользователь не ставил лайк на эту новость
            return ResponseEntity.badRequest().build(); // Возвращаем 400 Bad Request без тела
        }
    }
}