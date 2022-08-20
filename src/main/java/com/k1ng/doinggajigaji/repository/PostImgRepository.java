package com.k1ng.doinggajigaji.repository;

import com.k1ng.doinggajigaji.entity.Post;
import com.k1ng.doinggajigaji.entity.PostImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImgRepository extends JpaRepository<PostImg, Long> {
    List<PostImg> findByPostIdOrderByIdAsc(Long postId);
    List<PostImg> findAllByOrderByIdAsc();
    void deleteAllByPostId(Long postId);
}
