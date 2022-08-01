package com.k1ng.doinggajigaji.likes;


import com.k1ng.doinggajigaji.member.Member;
import com.k1ng.doinggajigaji.member.MemberRepository;
import com.k1ng.doinggajigaji.post.Post;
import com.k1ng.doinggajigaji.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public int getLikesCount(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return -1;
        }
        return likesRepository.countLikesByPost(post);
    }
}
