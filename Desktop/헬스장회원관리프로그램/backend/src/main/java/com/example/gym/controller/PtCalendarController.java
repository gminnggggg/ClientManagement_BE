package com.example.gym.controller;

import com.example.gym.dto.PtCalendarDto;
import com.example.gym.service.PtCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pt-calendar")
@RequiredArgsConstructor
public class PtCalendarController {

    private final PtCalendarService ptCalendarService;

    @GetMapping
    public ResponseEntity<List<PtCalendarDto.SessionResponse>> getMonthlySessions(
            @RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(ptCalendarService.getMonthlySessions(year, month));
    }

    @GetMapping("/day")
    public ResponseEntity<List<PtCalendarDto.SessionResponse>> getDailySessions(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ptCalendarService.getDailySessions(date));
    }
    
    // 이 외에 PT 일정 등록, 정기적 일정 생성, 임시 변경 등 API 추가 필요
}