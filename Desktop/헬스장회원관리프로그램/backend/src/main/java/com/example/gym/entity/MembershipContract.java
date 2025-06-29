package com.example.gym.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MembershipContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private String contractPeriod; // 예: "1개월", "3개월"

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_status") // 데이터베이스 예약어와 충돌을 피하기 위해 컬럼명 변경
    private ContractStatus status = ContractStatus.ACTIVE;

    public enum ContractStatus {
        ACTIVE, EXPIRED
    }
}