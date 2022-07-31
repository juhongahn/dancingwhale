package com.k1ng.doinggajigaji.post;

import java.util.List;

public interface PostService {
    Long post(PostFormDto postFormDto, String email);
    List<Post> findAllPost();
}
