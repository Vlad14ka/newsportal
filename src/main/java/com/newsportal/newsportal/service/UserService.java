package com.newsportal.newsportal.service;

import com.newsportal.newsportal.entity.Role;
import com.newsportal.newsportal.entity.User;
import com.newsportal.newsportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // Указывает Spring, что это компонент сервисного слоя
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Внедряем PasswordEncoder

    @Autowired // Автоматически внедряет зависимости
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Метод для регистрации нового пользователя
    public User registerNewUser(String username, String email, String password, Role role) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username is already taken!");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email is already in use!");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Хешируем пароль перед сохранением!
        user.setRole(role);
        return userRepository.save(user);
    }


    // Метод для поиска пользователя по имени пользователя
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Метод для сохранения пользователя
    public User saveUser(User user) {
        return userRepository.save(user);
    }


}