package com.newsportal.newsportal.service;

import com.newsportal.newsportal.entity.Like;
import com.newsportal.newsportal.entity.News;
import com.newsportal.newsportal.entity.User;
import com.newsportal.newsportal.repository.LikeRepository;
import com.newsportal.newsportal.repository.NewsRepository;
import com.newsportal.newsportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Transactional
    public Like addLike(Long newsId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + newsId));

        // Проверяем, не поставил ли пользователь уже лайк на эту новость
        if (likeRepository.findByUserAndNews(user, news).isPresent()) {
            throw new RuntimeException("User already liked this news.");
        }

        Like like = new Like(user, news);
        return likeRepository.save(like);
    }

    @Transactional
    public void removeLike(Long newsId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + newsId));

        // Проверяем, существует ли лайк, прежде чем удалять
        if (likeRepository.findByUserAndNews(user, news).isEmpty()) {
            throw new RuntimeException("User has not liked this news.");
        }

        likeRepository.deleteByUserAndNews(user, news);
    }

    public long getLikesCountForNews(Long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + newsId));
        return likeRepository.countByNews(news);
    }
}