package com.newsportal.newsportal.service;

import com.newsportal.newsportal.entity.Comment;
import com.newsportal.newsportal.entity.News;
import com.newsportal.newsportal.entity.User;
import com.newsportal.newsportal.repository.CommentRepository;
import com.newsportal.newsportal.repository.NewsRepository;
import com.newsportal.newsportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsRepository newsRepository;

    public List<Comment> getCommentsByNewsId(Long newsId) {
        return commentRepository.findByNewsId(newsId);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Transactional
    public Comment createComment(Long newsId, String text, String authorUsername) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + newsId));
        User author = userRepository.findByUsername(authorUsername)
                .orElseThrow(() -> new RuntimeException("Author not found: " + authorUsername));

        Comment comment = new Comment(text, author, news);
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long commentId, String newText) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        existingComment.setText(newText);
        // commentDate не меняем при обновлении
        return commentRepository.save(existingComment);
    }

    @Transactional
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Comment not found with id: " + id);
        }
        commentRepository.deleteById(id);
    }
}