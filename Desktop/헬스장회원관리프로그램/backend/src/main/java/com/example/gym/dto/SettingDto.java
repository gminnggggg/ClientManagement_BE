package com.example.gym.dto;

import lombok.Getter;
import lombok.Setter;

public class SettingDto {
    @Getter
    @Setter
    public static class ThemeRequest {
        private String color;
    }

    @Getter
    public static class ThemeResponse {
        private String color;
        public ThemeResponse(String color) { this.color = color; }
    }
}