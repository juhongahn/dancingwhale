package com.k1ng.doinggajigaji.service;

import com.k1ng.doinggajigaji.dto.PasswordChangeDto;
import com.k1ng.doinggajigaji.dto.ProfileEditDto;
import com.k1ng.doinggajigaji.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface MemberService {
    Member join(Member member);
    boolean findDuplication(String email);
    Member findMemberByEmail(String email);
    Member findMemberById(Long id);
    Long updateProfile(Long memberId, ProfileEditDto profileEditDto);
    Long updatePassword(Long memberId, PasswordChangeDto passwordChangeDto, PasswordEncoder passwordEncoder);
    boolean checkPassword(Long memberId, String currentPassword);
    Long deleteMember(Member member);
}
