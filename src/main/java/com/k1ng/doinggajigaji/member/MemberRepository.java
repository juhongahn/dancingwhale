package com.k1ng.doinggajigaji.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmail(String name);
    boolean existsByEmail(String email);
}
