package com.newsportal.newsportal.service;

import com.newsportal.newsportal.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    // Spring автоматически создаст RestTemplate bean, если он еще не определен.
    // Или можно создать его явно в конфигурации.
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getCurrentWeather(String city) {
        // Строим URL для запроса к OpenWeatherMap API
        // Пример: https://api.openweathermap.org/data/2.5/weather?q=London&appid=YOUR_API_KEY&units=metric
        String url = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("q", city) // Город
                .queryParam("appid", apiKey) // Твой API ключ
                .queryParam("units", "metric") // Единицы измерения (Celsius)
                .toUriString();

        try {
            // Выполняем GET запрос и мапим ответ на наш DTO
            return restTemplate.getForObject(url, WeatherResponse.class);
        } catch (Exception e) {
            // Логируем ошибку и/или бросаем свое исключение
            System.err.println("Error fetching weather data: " + e.getMessage());
            throw new RuntimeException("Could not fetch weather data for " + city, e);
        }
    }
}