package com.newsportal.newsportal.controller;

import com.newsportal.newsportal.dto.CommentRequest;
import com.newsportal.newsportal.dto.CommentResponse;
import com.newsportal.newsportal.entity.Comment;
import com.newsportal.newsportal.entity.User;
import com.newsportal.newsportal.service.CommentService;
import com.newsportal.newsportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Для авторизации на уровне метода
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments") // Доступен для всех, но некоторые операции требуют USER
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    // Получить все комментарии к новости (доступно всем)
    @GetMapping("/news/{newsId}")
    public List<CommentResponse> getCommentsByNewsId(@PathVariable Long newsId) {
        List<Comment> comments = commentService.getCommentsByNewsId(newsId);
        return comments.stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    // Создать комментарий к новости (только для аутентифицированных пользователей)
    @PostMapping("/news/{newsId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Только если пользователь USER или ADMIN
    public ResponseEntity<?> createComment(@PathVariable Long newsId,
                                           @Valid @RequestBody CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // Логин текущего пользователя

        try {
            Comment newComment = commentService.createComment(newsId, commentRequest.getText(), currentUsername);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CommentResponse(newComment));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Обновить комментарий (только для ADMIN или автора комментария)
    @PutMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or @commentServiceImpl.getCommentById(#commentId).orElse(null)?.author.username == authentication.name")
    // Сложная проверка: ADMIN ИЛИ автор комментария
    public ResponseEntity<?> updateComment(@PathVariable Long commentId,
                                           @Valid @RequestBody CommentRequest commentRequest) {
        try {
            Comment updatedComment = commentService.updateComment(commentId, commentRequest.getText());
            return ResponseEntity.ok(new CommentResponse(updatedComment));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    // Удалить комментарий (только для ADMIN или автора комментария)
    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or @commentServiceImpl.getCommentById(#commentId).orElse(null)?.author.username == authentication.name")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}