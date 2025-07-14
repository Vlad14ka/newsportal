package com.newsportal.newsportal;

import com.newsportal.newsportal.entity.Role;
import com.newsportal.newsportal.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NewsportalApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsportalApplication.class, args);
	}

	// --- НОВЫЙ БИН ДЛЯ СОЗДАНИЯ ТЕСТОВОГО АДМИНИСТРАТОРА ---
	@Bean
	public CommandLineRunner createAdminUser(UserService userService) {
		return args -> {
			// Проверяем, существует ли уже администратор с таким именем пользователя
			if (userService.findByUsername("admin").isEmpty()) {
				System.out.println("Создаем тестового администратора...");
				userService.registerNewUser("admin", "admin@example.com", "adminpass", Role.ADMIN);
				System.out.println("Тестовый администратор создан: логин 'admin', пароль 'adminpass'");
			}
		};
	}
	// -----------------------------------------------------
}