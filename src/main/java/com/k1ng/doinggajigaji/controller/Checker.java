package com.k1ng.doinggajigaji.controller;

import com.k1ng.doinggajigaji.entity.Member;
import com.k1ng.doinggajigaji.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class Checker {
    private final MemberRepository memberRepository;

    public boolean isSelf(Long memberId) {
        Member member = memberRepository.findById(memberId).
                orElseThrow(EntityNotFoundException::new);
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();

        User principal = (User) authentication.getPrincipal();

        return member.getEmail().equals(principal.getPassword());
    }
}
