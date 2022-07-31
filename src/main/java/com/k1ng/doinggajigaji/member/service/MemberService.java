package com.k1ng.doinggajigaji.member.service;

import com.k1ng.doinggajigaji.member.Member;
import com.k1ng.doinggajigaji.member.dto.MemberFormDto;

public interface MemberService {
    Long join(MemberFormDto memberFormDto);
    boolean findDuplication(String email);
    Member findMemberByEmail(String email);
}
