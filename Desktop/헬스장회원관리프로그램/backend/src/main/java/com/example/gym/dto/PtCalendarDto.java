package com.example.gym.dto;

import com.example.gym.entity.PtSession;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class PtCalendarDto {

    @Getter
    @Builder
    public static class SessionResponse {
        private Long sessionId;
        private Long memberId;
        private String memberName;
        private LocalDateTime start; // FullCalendar 호환을 위해 'start' 사용
        private LocalDateTime end;

        public static SessionResponse fromEntity(PtSession session) {
            return SessionResponse.builder()
                    .sessionId(session.getId())
                    .memberId(session.getMember().getId())
                    .memberName(session.getMember().getName())
                    .start(session.getSessionDateTime())
                    .end(session.getSessionDateTime().plusHours(1)) // 1시간으로 가정
                    .build();
        }
    }
}