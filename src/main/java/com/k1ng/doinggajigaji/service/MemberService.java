package com.k1ng.doinggajigaji.service;

import com.k1ng.doinggajigaji.dto.PasswordChangeDto;
import com.k1ng.doinggajigaji.dto.ProfileEditDto;
import com.k1ng.doinggajigaji.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MemberService {
    Member join(Member member, String siteURL) throws MessagingException, UnsupportedEncodingException;
    boolean findDuplication(String email);
    Member findMemberByEmail(String email);
    Member findMemberById(Long id);
    boolean verify(String verificationCode);
    Long updateProfile(Long memberId, ProfileEditDto profileEditDto);
    Long updatePassword(Long memberId, PasswordChangeDto passwordChangeDto, PasswordEncoder passwordEncoder);
    Long deleteMember(Member member);
}
