package com.example.gym.service;

import com.example.gym.dto.AdminDto;
import com.example.gym.entity.Admin;
import com.example.gym.repository.AdminRepository;
import com.example.gym.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(AdminDto.SignupRequest requestDto) {
        // 관리자 계정은 하나만 생성 가능하도록 제한
        if (adminRepository.count() > 0) {
            throw new IllegalStateException("관리자 계정은 하나만 생성할 수 있습니다.");
        }
        if (adminRepository.existsByUsername(requestDto.getUsername())) {
             throw new IllegalStateException("이미 존재하는 사용자 이름입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        Admin admin = new Admin(requestDto.getUsername(), encodedPassword);
        adminRepository.save(admin);
    }

    public String login(AdminDto.LoginRequest requestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword())
        );
        // 인증 성공 시 토큰 발급
        return jwtUtil.createToken(requestDto.getUsername());
    }

    public boolean verifyAdminPassword(String username, String rawPassword) {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("관리자를 찾을 수 없습니다."));
        return passwordEncoder.matches(rawPassword, admin.getPassword());
    }
}