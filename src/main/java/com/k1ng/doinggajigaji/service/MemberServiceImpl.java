package com.k1ng.doinggajigaji.service;


import com.k1ng.doinggajigaji.dto.PasswordChangeDto;
import com.k1ng.doinggajigaji.dto.ProfileEditDto;
import com.k1ng.doinggajigaji.entity.Member;
import com.k1ng.doinggajigaji.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements UserDetailsService, MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member join(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public boolean findDuplication(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public Member findMemberByEmail(String email) {
        
        // 찾고 없을 땐 null을 반환.
        return memberRepository.findMemberByEmail(email).orElse(null);
    }

    @Override
    public Member findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Long updateProfile(Long memberId, ProfileEditDto profileEditDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);
        member.updateProfile(profileEditDto);
        return member.getId();
    }

    @Override
    public Long updatePassword(Long memberId, PasswordChangeDto passwordChangeDto, PasswordEncoder passwordEncoder) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);
        member.updatePassword(passwordChangeDto, passwordEncoder);
        return member.getId();
    }
    @Override
    public boolean checkPassword(Long memberId, String currentPassword) {
        Member foundMember = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
        log.info("currentPassword={}, member password = {}", currentPassword, foundMember.getPassword());
        return foundMember.getPassword().equals(currentPassword);
    }

    @Override
    public Long deleteMember(Member member) {
        memberRepository.delete(member);
        return member.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
