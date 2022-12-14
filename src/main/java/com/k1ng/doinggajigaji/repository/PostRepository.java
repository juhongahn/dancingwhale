package com.k1ng.doinggajigaji.repository;

import com.k1ng.doinggajigaji.entity.Member;
import com.k1ng.doinggajigaji.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 인덱스 페이지에 쏴줄 데이터
    //List<Post> findAllByOnlyMeFalseOrMemberOrderByRegTimeDesc(Member member);

    Page<Post> findAllByOnlyMeFalseOrMemberOrderByRegTimeDesc(Member member, Pageable pageable);

    // 멤버별 post List
    Page<Post> findAllByMemberOrderByRegTimeDesc(Member member, Pageable pageable);
}
