package com.k1ng.doinggajigaji.member.login;

import com.k1ng.doinggajigaji.member.Member;
import com.k1ng.doinggajigaji.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(LoginFormDto loginFormDto) {

        return memberRepository.findMemberByEmail(loginFormDto.getEmail())
                .filter(m -> m.getPassword().equals(loginFormDto.getPassword()))
                .orElse(null);
    }
}
