package com.example.gym.repository;

import com.example.gym.entity.MembershipContract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipContractRepository extends JpaRepository<MembershipContract, Long> {
}