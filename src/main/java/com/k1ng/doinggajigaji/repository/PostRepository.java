package com.k1ng.doinggajigaji.repository;

import com.k1ng.doinggajigaji.entity.Member;
import com.k1ng.doinggajigaji.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 인덱스 페이지에 쏴줄 데이터
    List<Post> findAllByOnlyMeFalseOrMemberOrderByRegTimeDesc(Member member);

    // 멤버별 post List
    List<Post> findAllByMember(Member member);
}
