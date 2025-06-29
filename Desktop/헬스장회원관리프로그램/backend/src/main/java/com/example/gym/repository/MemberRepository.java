package com.example.gym.repository;

import com.example.gym.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 이름 또는 전화번호로 회원 검색
    List<Member> findByNameContainingOrPhoneNumberContaining(String name, String phoneNumber);

    // 만기 임박 회원 조회 (가장 최근 계약 기준)
    @Query("SELECT m FROM Member m JOIN m.contracts c WHERE m.id = c.member.id AND c.endDate BETWEEN :today AND :sevenDaysLater GROUP BY m.id HAVING MAX(c.endDate) BETWEEN :today AND :sevenDaysLater")
    List<Member> findMembersWithExpiringContracts(@Param("today") LocalDate today, @Param("sevenDaysLater") LocalDate sevenDaysLater);
}