package com.k1ng.doinggajigaji.service;


import com.k1ng.doinggajigaji.entity.Likes;
import com.k1ng.doinggajigaji.entity.Member;
import com.k1ng.doinggajigaji.entity.Post;
import com.k1ng.doinggajigaji.repository.LikesRepository;
import com.k1ng.doinggajigaji.repository.MemberRepository;
import com.k1ng.doinggajigaji.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

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

        Likes save = likesRepository.save(likes);
        return save.getId();
    }

    @Override
    public boolean checkDuplicatedLikes(String userEmail, Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        // 중복되면 true, 아니면 false
        return post.getLikesList().stream().anyMatch(likes -> likes.getMember().getEmail().equals(userEmail));
    }


}
