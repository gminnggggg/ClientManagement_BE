package com.example.gym.service;

import com.example.gym.entity.AppSetting;
import com.example.gym.repository.AppSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final AppSettingRepository settingRepository;
    private static final String THEME_KEY = "THEME_COLOR";

    public String getThemeColor() {
        return settingRepository.findById(THEME_KEY)
                .map(AppSetting::getSettingValue)
                .orElse("#FFFFFF"); // 기본값
    }

    public void setThemeColor(String color) {
        AppSetting themeSetting = settingRepository.findById(THEME_KEY)
                .orElse(new AppSetting(THEME_KEY, color));
        themeSetting.setSettingValue(color);
        settingRepository.save(themeSetting);
    }
}