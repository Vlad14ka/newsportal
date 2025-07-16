package com.newsportal.newsportal.service;

import com.newsportal.newsportal.entity.News;
import com.newsportal.newsportal.entity.User;
import com.newsportal.newsportal.repository.NewsRepository;
import com.newsportal.newsportal.repository.UserRepository; // Потребуется для получения автора
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional; // Важно для операций изменения данных

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository; // Для связи с автором

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public Optional<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }

    @Transactional // Делает метод транзакционным (для операций изменения)
    public News createNews(String title, String content, String authorUsername) {
        User author = userRepository.findByUsername(authorUsername)
                .orElseThrow(() -> new RuntimeException("Author not found: " + authorUsername));

        News news = new News(title, content, author);
        // Дата публикации уже устанавливается в конструкторе News
        return newsRepository.save(news);
    }

    @Transactional
    public News updateNews(Long id, String title, String content) {
        News existingNews = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id));

        existingNews.setTitle(title);
        existingNews.setContent(content);
        // publishDate не меняем при обновлении, можно добавить поле lastModifiedDate если нужно
        return newsRepository.save(existingNews);
    }

    @Transactional
    public void deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new RuntimeException("News not found with id: " + id);
        }
        newsRepository.deleteById(id);
    }
}