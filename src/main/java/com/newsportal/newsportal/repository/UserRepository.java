package com.newsportal.newsportal.repository;

import com.newsportal.newsportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// JpaRepository предоставляет готовые методы для CRUD операций
public interface UserRepository extends JpaRepository<User, Long> {

    // Дополнительный метод для поиска пользователя по имени пользователя
    Optional<User> findByUsername(String username);

    // Дополнительный метод для проверки существования пользователя по имени пользователя
    Boolean existsByUsername(String username);

    // Дополнительный метод для проверки существования пользователя по email
    Boolean existsByEmail(String email);
}