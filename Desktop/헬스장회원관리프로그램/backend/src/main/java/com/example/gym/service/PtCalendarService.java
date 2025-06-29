package com.example.gym.service;

import com.example.gym.dto.PtCalendarDto;
import com.example.gym.entity.PtSession;
import com.example.gym.repository.PtSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PtCalendarService {

    private final PtSessionRepository ptSessionRepository;

    public List<PtCalendarDto.SessionResponse> getMonthlySessions(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        return ptSessionRepository.findBySessionDateTimeBetween(startOfMonth, endOfMonth)
                .stream()
                .map(PtCalendarDto.SessionResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<PtCalendarDto.SessionResponse> getDailySessions(LocalDate date) {
        return ptSessionRepository.findBySessionDate(date.atStartOfDay())
                .stream()
                .map(PtCalendarDto.SessionResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public void changePtSessionTemporarily(Long sessionId, LocalDateTime newDateTime) {
        // 로직 구현: 특정 세션을 임시로 변경
        PtSession session = ptSessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("PT 세션을 찾을 수 없습니다."));
        
        // TODO: 1주일 기간만 변경 가능한지 등 비즈니스 로직 추가
        
        session.setSessionDateTime(newDateTime);
        session.setTemporary(true);
        ptSessionRepository.save(session);
    }
}