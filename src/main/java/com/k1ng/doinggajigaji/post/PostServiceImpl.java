package com.k1ng.doinggajigaji.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Override
    public Long post(PostFormDto postFormDto) {

        Post post = new Post(postFormDto.getDescription());
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }
}
