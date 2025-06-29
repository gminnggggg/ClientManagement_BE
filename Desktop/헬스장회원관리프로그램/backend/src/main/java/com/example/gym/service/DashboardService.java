package com.example.gym.service;

import com.example.gym.dto.MemberDto;
import com.example.gym.dto.PtCalendarDto;
import com.example.gym.entity.Member;
import com.example.gym.entity.MembershipContract;
import com.example.gym.repository.MemberRepository;
import com.example.gym.repository.PtSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final PtSessionRepository ptSessionRepository;
    private final MemberRepository memberRepository;

    public List<PtCalendarDto.SessionResponse> getTodayPtSessions() {
        LocalDateTime today = LocalDateTime.now();
        return ptSessionRepository.findBySessionDate(today).stream()
                .map(PtCalendarDto.SessionResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<MemberDto.SimpleResponse> getExpiringSoonMembers() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysLater = today.plusDays(7);
        
        List<Member> expiringMembers = memberRepository.findMembersWithExpiringContracts(today, sevenDaysLater);

        return expiringMembers.stream()
                .map(member -> {
                     LocalDate latestEndDate = member.getContracts().stream()
                            .map(MembershipContract::getEndDate)
                            .max(LocalDate::compareTo)
                            .orElse(null);
                    return MemberDto.SimpleResponse.fromEntity(member, latestEndDate);
                })
                .collect(Collectors.toList());
    }
}