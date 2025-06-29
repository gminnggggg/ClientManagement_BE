package com.example.gym.controller;

import com.example.gym.dto.SettingDto;
import com.example.gym.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingController {
    
    private final SettingService settingService;
    
    @GetMapping("/theme")
    public ResponseEntity<SettingDto.ThemeResponse> getTheme() {
        String color = settingService.getThemeColor();
        return ResponseEntity.ok(new SettingDto.ThemeResponse(color));
    }
    
    @PutMapping("/theme")
    public ResponseEntity<Void> setTheme(@RequestBody SettingDto.ThemeRequest dto) {
        settingService.setThemeColor(dto.getColor());
        return ResponseEntity.ok().build();
    }
}