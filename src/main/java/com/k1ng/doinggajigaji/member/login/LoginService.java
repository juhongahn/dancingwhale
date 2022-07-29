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
        Member foundMember = memberRepository.findMemberByEmail(loginFormDto.getEmail());

        // 로그인 실패
        if (!foundMember.getPassword().equals(loginFormDto.getPassword())) {
            return null;
        }
        // 로그인 성공
        return foundMember;
    }
}
