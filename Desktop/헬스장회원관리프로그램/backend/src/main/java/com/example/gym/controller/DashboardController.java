package com.example.gym.controller;

import com.example.gym.dto.MemberDto;
import com.example.gym.dto.PtCalendarDto;
import com.example.gym.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/pt-today")
    public ResponseEntity<List<PtCalendarDto.SessionResponse>> getTodayPtSessions() {
        return ResponseEntity.ok(dashboardService.getTodayPtSessions());
    }

    @GetMapping("/expiring-soon")
    public ResponseEntity<List<MemberDto.SimpleResponse>> getExpiringSoonMembers() {
        return ResponseEntity.ok(dashboardService.getExpiringSoonMembers());
    }
}