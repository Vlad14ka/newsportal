package com.newsportal.newsportal.repository;

import com.newsportal.newsportal.entity.Like;
import com.newsportal.newsportal.entity.News;
import com.newsportal.newsportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    // Найти лайк конкретного пользователя на конкретной новости
    Optional<Like> findByUserAndNews(User user, News news);

    // Посчитать количество лайков для конкретной новости
    long countByNews(News news);

    // Удалить лайк конкретного пользователя на конкретной новости
    void deleteByUserAndNews(User user, News news);
}