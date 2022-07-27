package com.k1ng.doinggajigaji.member;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Long join(Member member) {
        Member save = memberRepository.save(member);
        return save.getId();
    }
}
