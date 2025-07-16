package com.newsportal.newsportal.repository;

import com.newsportal.newsportal.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Найти все комментарии для конкретной новости
    List<Comment> findByNewsId(Long newsId);
}