package com.example.gym.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class AdminDto {
    @Getter
    @Setter
    public static class SignupRequest {
        @NotBlank(message = "사용자 이름은 필수입니다.")
        private String username;
        @NotBlank(message = "비밀번호는 필수입니다.")
        private String password;
    }

    @Getter
    @Setter
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Getter
    @Setter
    public static class LoginResponse {
        private String token;
        public LoginResponse(String token) { this.token = token; }
    }

    @Getter
    @Setter
    public static class VerifyRequest {
        private String password;
    }
}