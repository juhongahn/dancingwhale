package com.k1ng.doinggajigaji.repository;

import com.k1ng.doinggajigaji.entity.Likes;
import com.k1ng.doinggajigaji.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    int countLikesByPost(Post post);
}
