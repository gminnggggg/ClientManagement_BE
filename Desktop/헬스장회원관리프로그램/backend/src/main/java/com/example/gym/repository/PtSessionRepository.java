package com.example.gym.repository;

import com.example.gym.entity.PtSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;


public interface PtSessionRepository extends JpaRepository<PtSession, Long> {
    // 특정 기간 사이의 PT 일정 조회
    List<PtSession> findBySessionDateTimeBetween(LocalDateTime start, LocalDateTime end);

    // 특정 날짜의 PT 일정 조회
    @Query("SELECT p FROM PtSession p WHERE FUNCTION('DATE', p.sessionDateTime) = FUNCTION('DATE', :date)")
    List<PtSession> findBySessionDate(LocalDateTime date);
}