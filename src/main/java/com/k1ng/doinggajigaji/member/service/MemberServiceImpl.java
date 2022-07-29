package com.k1ng.doinggajigaji.member.service;


import com.k1ng.doinggajigaji.member.Member;
import com.k1ng.doinggajigaji.member.MemberRepository;
import com.k1ng.doinggajigaji.member.dto.MemberFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Long join(MemberFormDto memberFormDto) {
        Member member = Member.formToMember(memberFormDto);
        memberRepository.save(member);
        return member.getId();
    }

    @Override
    public boolean findDuplication(String email) {
        return memberRepository.existsByEmail(email);
    }



}
