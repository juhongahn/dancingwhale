package com.k1ng.doinggajigaji.repository;

import com.k1ng.doinggajigaji.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmail(String name);
    boolean existsByEmail(String email);
    Member findByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.verificationCode = ?1")
    public Member findByVerificationCode(String code);
}
