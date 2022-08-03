package com.k1ng.doinggajigaji.service;

import com.k1ng.doinggajigaji.dto.CardFormDto;
import com.k1ng.doinggajigaji.dto.PostFormDto;
import com.k1ng.doinggajigaji.entity.Member;
import com.k1ng.doinggajigaji.entity.Post;
import com.k1ng.doinggajigaji.repository.MemberRepository;
import com.k1ng.doinggajigaji.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long savePost(PostFormDto postFormDto, String email) throws Exception {

        // 게시물 등록
        Post post = postFormDto.createPost();
        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        post.setMember(member);
        postRepository.save(post);

        return post.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CardFormDto> getAllCardForm() {

        return postRepository.findAllByOrderByRegTimeDesc().stream()
                .map((post)-> new CardFormDto().createCardForm(post)).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    @Override
    public PostFormDto findPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        return PostFormDto.of(post);
    }

    @Override
    public Long updatePost(PostFormDto postFormDto) {

        Post post = postRepository.findById(postFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        post.update(postFormDto);
        return post.getId();
    }


}
