package com.example.gym.controller;

import com.example.gym.dto.AdminDto;
import com.example.gym.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AdminDto.SignupRequest requestDto) {
        try {
            adminService.signup(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("관리자 계정이 생성되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AdminDto.LoginResponse> login(@RequestBody AdminDto.LoginRequest requestDto) {
        String token = adminService.login(requestDto);
        return ResponseEntity.ok(new AdminDto.LoginResponse(token));
    }
}