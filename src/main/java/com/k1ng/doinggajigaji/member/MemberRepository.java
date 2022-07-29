package com.k1ng.doinggajigaji.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberByEmail(String name);
    boolean existsByEmail(String email);
}
