package com.k1ng.doinggajigaji.post;

import com.k1ng.doinggajigaji.member.Member;
import com.k1ng.doinggajigaji.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final MemberService memberService;

    @Override
    public Long post(PostFormDto postFormDto, String email) {

        Member member = memberService.findMemberByEmail(email);

        Post post = new Post();
        post.setDescription(postFormDto.getDescription());
        post.setMember(member);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        return savedPost.getId();
    }

    @Override
    public List<Post> findAllByOrderByCreatedAtDesc() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

}
