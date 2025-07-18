package com.newsportal.newsportal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Для игнорирования ненужных полей
import com.fasterxml.jackson.annotation.JsonProperty; // Для маппинга JSON полей

@JsonIgnoreProperties(ignoreUnknown = true) // Игнорировать поля, которых нет в DTO
public class WeatherResponse {

    @JsonProperty("name")
    private String cityName;

    @JsonProperty("main")
    private MainWeatherInfo main;

    @JsonProperty("weather")
    private WeatherDescription[] weather; // Массив, так как может быть несколько описаний

    // Геттеры и сеттеры (сгенерируй через IDE)
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public MainWeatherInfo getMain() {
        return main;
    }

    public void setMain(MainWeatherInfo main) {
        this.main = main;
    }

    public WeatherDescription[] getWeather() {
        return weather;
    }

    public void setWeather(WeatherDescription[] weather) {
        this.weather = weather;
    }

    // Вспомогательные методы для удобства
    public String getDescription() {
        if (weather != null && weather.length > 0) {
            return weather[0].getDescription();
        }
        return "No description";
    }

    public double getTemperature() {
        return main != null ? main.getTemp() : 0.0;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class MainWeatherInfo {
    @JsonProperty("temp")
    private double temp;

    @JsonProperty("feels_like")
    private double feelsLike;

    @JsonProperty("humidity")
    private int humidity;

    // Геттеры и сеттеры (сгенерируй через IDE)
    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class WeatherDescription {
    @JsonProperty("description")
    private String description;

    @JsonProperty("icon")
    private String icon;

    // Геттеры и сеттеры (сгенерируй через IDE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}