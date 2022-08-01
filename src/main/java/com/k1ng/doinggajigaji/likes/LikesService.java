package com.k1ng.doinggajigaji.likes;

public interface LikesService {
    Long newLikes(String userEmail, Long postId);
    int getLikesCount(Long postId);
}
