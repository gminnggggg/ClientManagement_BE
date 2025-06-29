package com.example.gym.dto;

import com.example.gym.entity.Member;
import com.example.gym.entity.MembershipContract;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MemberDto {

    @Getter
    @Setter
    public static class CreateRequest {
        @NotBlank
        private String name;
        @NotBlank
        private String phoneNumber;
        private LocalDate birthDate;
        @NotNull
        private Member.MemberType memberType;
        private String lockerNumber;
        @NotBlank
        private String contractPeriod; // 예: "1개월"
        @NotNull
        private LocalDate startDate;
        // Pt 일정은 별도 API로 관리
    }

    @Getter
    @Setter
    public static class UpdateRequest {
        private String name;
        private String phoneNumber;
        private LocalDate birthDate;
        private String lockerNumber;
    }

    @Getter
    @Setter
    public static class ReRegisterRequest {
        @NotBlank
        private String contractPeriod;
        @NotNull
        private LocalDate startDate;
    }


    @Getter
    @Builder
    public static class DetailResponse {
        private Long id;
        private String name;
        private String phoneNumber;
        private LocalDate birthDate;
        private Member.MemberType memberType;
        private String lockerNumber;
        private LocalDate contractStartDate;
        private LocalDate contractEndDate;
        private String contractPeriod;

        public static DetailResponse fromEntity(Member member, MembershipContract latestContract) {
            return DetailResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .birthDate(member.getBirthDate())
                .memberType(member.getMemberType())
                .lockerNumber(member.getLockerNumber())
                .contractStartDate(latestContract.getStartDate())
                .contractEndDate(latestContract.getEndDate())
                .contractPeriod(latestContract.getContractPeriod())
                .build();
        }
    }
    
    @Getter
    @Builder
    public static class SimpleResponse {
        private Long id;
        private String name;
        private String phoneNumber;
        private LocalDate contractEndDate;

        public static SimpleResponse fromEntity(Member member, LocalDate endDate) {
            return SimpleResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .contractEndDate(endDate)
                .build();
        }
    }
}
