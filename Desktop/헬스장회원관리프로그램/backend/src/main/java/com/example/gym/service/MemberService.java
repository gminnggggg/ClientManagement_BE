package com.example.gym.service;

import com.example.gym.dto.MemberDto;
import com.example.gym.entity.Member;
import com.example.gym.entity.MembershipContract;
import com.example.gym.repository.MemberRepository;
import com.example.gym.repository.MembershipContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MembershipContractRepository contractRepository;

    @Transactional
    public Long createMember(MemberDto.CreateRequest dto) {
        // 회원 생성
        Member member = new Member();
        member.setName(dto.getName());
        member.setPhoneNumber(dto.getPhoneNumber());
        member.setBirthDate(dto.getBirthDate());
        member.setMemberType(dto.getMemberType());
        member.setLockerNumber(dto.getLockerNumber());
        
        // 계약 생성
        MembershipContract contract = createContractFromDto(member, dto.getStartDate(), dto.getContractPeriod());
        member.getContracts().add(contract);

        memberRepository.save(member);
        return member.getId();
    }
    
    @Transactional
    public Long reRegisterMember(Long memberId, MemberDto.ReRegisterRequest dto) {
        Member member = findMemberById(memberId);
        MembershipContract contract = createContractFromDto(member, dto.getStartDate(), dto.getContractPeriod());
        contractRepository.save(contract);
        return contract.getId();
    }

    public List<MemberDto.SimpleResponse> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(member -> {
                    LocalDate latestEndDate = member.getContracts().stream()
                            .map(MembershipContract::getEndDate)
                            .max(LocalDate::compareTo)
                            .orElse(null);
                    return MemberDto.SimpleResponse.fromEntity(member, latestEndDate);
                })
                .collect(Collectors.toList());
    }
    
    public List<MemberDto.SimpleResponse> searchMembers(String query) {
         return memberRepository.findByNameContainingOrPhoneNumberContaining(query, query).stream()
                 .map(member -> {
                    LocalDate latestEndDate = member.getContracts().stream()
                            .map(MembershipContract::getEndDate)
                            .max(LocalDate::compareTo)
                            .orElse(null);
                    return MemberDto.SimpleResponse.fromEntity(member, latestEndDate);
                })
                .collect(Collectors.toList());
    }


    public MemberDto.DetailResponse getMemberDetail(Long id) {
        Member member = findMemberById(id);
        MembershipContract latestContract = member.getContracts().stream()
                .max(Comparator.comparing(MembershipContract::getEndDate))
                .orElseThrow(() -> new IllegalStateException("해당 회원의 계약 정보를 찾을 수 없습니다."));
        return MemberDto.DetailResponse.fromEntity(member, latestContract);
    }
    
    @Transactional
    public void updateMember(Long id, MemberDto.UpdateRequest dto) {
        Member member = findMemberById(id);
        member.setName(dto.getName());
        member.setPhoneNumber(dto.getPhoneNumber());
        member.setBirthDate(dto.getBirthDate());
        member.setLockerNumber(dto.getLockerNumber());
    }
    
    @Transactional
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    // --- Helper Methods ---
    
    private Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. ID: " + id));
    }

    private MembershipContract createContractFromDto(Member member, LocalDate startDate, String contractPeriod) {
        MembershipContract contract = new MembershipContract();
        contract.setMember(member);
        contract.setStartDate(startDate);
        contract.setContractPeriod(contractPeriod);
        contract.setEndDate(calculateEndDate(startDate, contractPeriod));
        return contract;
    }
    
    private LocalDate calculateEndDate(LocalDate startDate, String contractPeriod) {
        // "1개월", "3개월", "1년" 등의 문자열을 파싱하여 기간 계산
        // 간단한 예시:
        if (contractPeriod.contains("개월")) {
            int months = Integer.parseInt(contractPeriod.replaceAll("[^0-9]", ""));
            return startDate.plusMonths(months);
        } else if (contractPeriod.contains("년")) {
            int years = Integer.parseInt(contractPeriod.replaceAll("[^0-9]", ""));
            return startDate.plusYears(years);
        }
        // 기본값: 한 달
        return startDate.plusMonths(1);
    }
}