package com.k1ng.doinggajigaji;

import com.k1ng.doinggajigaji.dto.MemberFormDto;
import com.k1ng.doinggajigaji.entity.Member;
import com.k1ng.doinggajigaji.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;

    @PostConstruct
    public void init() {
        memberService.join(Member.formToMember(new MemberFormDto("안주홍", "12345678",
                "lock5028@naver.com", "scarlet")));

    }
}
