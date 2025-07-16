package com.newsportal.newsportal.repository;

import com.newsportal.newsportal.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
    // Здесь можно добавить пользовательские методы для поиска новостей, если понадобится
    // Например: List<News> findByTitleContaining(String title);
}