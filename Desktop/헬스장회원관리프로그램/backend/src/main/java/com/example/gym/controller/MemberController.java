package com.example.gym.controller;

import com.example.gym.dto.AdminDto;
import com.example.gym.dto.MemberDto;
import com.example.gym.service.AdminService;
import com.example.gym.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<Long> createMember(@RequestBody MemberDto.CreateRequest dto) {
        return ResponseEntity.ok(memberService.createMember(dto));
    }
    
    @PostMapping("/{id}/re-register")
    public ResponseEntity<Long> reRegisterMember(@PathVariable Long id, @RequestBody MemberDto.ReRegisterRequest dto) {
        return ResponseEntity.ok(memberService.reRegisterMember(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<MemberDto.SimpleResponse>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<MemberDto.SimpleResponse>> searchMembers(@RequestParam String query) {
        return ResponseEntity.ok(memberService.searchMembers(query));
    }

    @PostMapping("/verify-admin")
    public ResponseEntity<Boolean> verifyAdminPassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AdminDto.VerifyRequest dto) {
        boolean isVerified = adminService.verifyAdminPassword(userDetails.getUsername(), dto.getPassword());
        return ResponseEntity.ok(isVerified);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MemberDto.DetailResponse> getMemberDetail(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberDetail(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMember(@PathVariable Long id, @RequestBody MemberDto.UpdateRequest dto) {
        memberService.updateMember(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}