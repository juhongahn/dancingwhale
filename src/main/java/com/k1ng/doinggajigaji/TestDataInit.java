package com.k1ng.doinggajigaji;

import com.k1ng.doinggajigaji.member.dto.MemberFormDto;
import com.k1ng.doinggajigaji.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;

    @PostConstruct
    public void init() {
        memberService.join(new MemberFormDto("안주홍", "12345678",
                "lock5028@naver.com", "scarlet"));

    }
}
