package com.newsportal.newsportal.controller;

import com.newsportal.newsportal.dto.WeatherResponse;
import com.newsportal.newsportal.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather") // Базовый путь для погодного API
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    // Эндпоинт для получения текущей погоды по названию города
    // Пример: http://localhost:8080/api/weather/current?city=Warsaw
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentWeather(@RequestParam String city) {
        try {
            WeatherResponse weather = weatherService.getCurrentWeather(city);
            return ResponseEntity.ok(weather);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Error fetching weather data: " + e.getMessage());
        }
    }
}