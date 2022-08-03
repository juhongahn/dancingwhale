package com.k1ng.doinggajigaji.service;


import com.k1ng.doinggajigaji.dto.PasswordChangeDto;
import com.k1ng.doinggajigaji.dto.ProfileEditDto;
import com.k1ng.doinggajigaji.entity.Member;
import com.k1ng.doinggajigaji.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Member join(Member member) {
        validateDuplicateMember(member);
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
    public Long updatePassword(Long memberId, PasswordChangeDto passwordChangeDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);
        member.updatePassword(passwordChangeDto);

        return member.getId();
    }
    @Override
    public boolean checkPassword(Long memberId, String currentPassword) {
        Member foundMember = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
        return foundMember.getPassword().equals(currentPassword);
    }

    private void validateDuplicateMember(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

}
