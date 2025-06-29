package com.example.gym.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PtSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private LocalDateTime sessionDateTime;

    private boolean isTemporary = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_status") // 데이터베이스 예약어와 충돌을 피하기 위해 컬럼명 변경
    private SessionStatus status = SessionStatus.SCHEDULED;

    public enum SessionStatus {
        SCHEDULED, COMPLETED, CANCELED
    }
}