package com.k1ng.doinggajigaji.service;


import com.k1ng.doinggajigaji.entity.Likes;
import com.k1ng.doinggajigaji.entity.Member;
import com.k1ng.doinggajigaji.repository.LikesRepository;
import com.k1ng.doinggajigaji.repository.MemberRepository;
import com.k1ng.doinggajigaji.entity.Post;
import com.k1ng.doinggajigaji.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class LikesServiceImpl implements LikesService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final LikesRepository likesRepository;

    @Override
    public Long newLikes(String userEmail, Long postId) {

        Member member = memberRepository.findMemberByEmail(userEmail).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);
        Likes likes = new Likes();
        likes.setMember(member);
        likes.setPost(post);
        likes.setCreatedAt(LocalDateTime.now());

        Likes save = likesRepository.save(likes);
        return save.getId();
    }

    @Override
    public boolean checkDuplicatedLikes(String userEmail, Long postId) {


        return false;
    }


}
