package com.k1ng.doinggajigaji.likes;

import com.k1ng.doinggajigaji.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    int countLikesByPost(Post post);
}
