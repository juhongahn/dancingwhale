package com.k1ng.doinggajigaji.service;

public interface LikesService {
    Long newLikes(String userEmail, Long postId);
    boolean checkDuplicatedLikes(String userEmail, Long postId);

}
