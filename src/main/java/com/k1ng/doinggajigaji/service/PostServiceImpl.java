package com.k1ng.doinggajigaji.service;

import com.k1ng.doinggajigaji.dto.CardFormDto;
import com.k1ng.doinggajigaji.dto.PostFormDto;
import com.k1ng.doinggajigaji.dto.PostImgDto;
import com.k1ng.doinggajigaji.entity.Member;
import com.k1ng.doinggajigaji.entity.Post;
import com.k1ng.doinggajigaji.entity.PostImg;
import com.k1ng.doinggajigaji.repository.MemberRepository;
import com.k1ng.doinggajigaji.repository.PostImgRepository;
import com.k1ng.doinggajigaji.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostImgService postImgService;
    private final PostImgRepository postImgRepository;

    @Override
    public Long savePost(PostFormDto postFormDto, String email, List<MultipartFile> itemImgFileList) throws Exception {

        // 게시물 등록
        Post post = postFormDto.createPost();
        Member member = memberRepository.findMemberByEmail(email).orElseThrow(EntityNotFoundException::new);
        post.setMember(member);
        postRepository.save(post);

        log.info(String.valueOf(itemImgFileList.size()));

        if (!Objects.equals(itemImgFileList.get(0).getOriginalFilename(), "")) {
            for (MultipartFile multipartFile : itemImgFileList) {
                PostImg postImg = new PostImg();
                postImg.setPost(post);
                postImgService.savePostImg(postImg, multipartFile);
            }
        }
        return post.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public PostFormDto findPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        return PostFormDto.of(post);
    }

    @Override
    public Long updatePost(PostFormDto postFormDto, List<MultipartFile> postImgFileList) throws IOException {

        Post post = postRepository.findById(postFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        post.updatePost(postFormDto);

        List<Long> postImgIds = postFormDto.getPostImgIds();

        // 이미지 등록
        for (int i = 0; i < postImgFileList.size(); i++) {
            postImgService.updatePostImg(postImgIds.get(i), postImgFileList.get(i));
        }
        return post.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public PostFormDto getPostDtl(Long postId) {

        List<PostImg> postImgList = postImgRepository.findByPostIdOrderByIdAsc(postId);

        List<PostImgDto> postImgDtoList = new ArrayList<>();
        // entity => dto
        for (PostImg postImg : postImgList) {
            PostImgDto postImgDto = PostImgDto.of(postImg);
            postImgDtoList.add(postImgDto);
        }

        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        PostFormDto postFormDto = PostFormDto.of(post);
        postFormDto.setPostImgDtoList(postImgDtoList);
        return postFormDto;
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.delete(postRepository.findById(postId).orElseThrow(EntityNotFoundException::new));
    }


    @Transactional(readOnly = true)
    @Override
    public List<CardFormDto> getAllCardForm(String email) {

        List<PostImg> postImgList = postImgRepository.findAllByOrderByIdAsc();

        Member byEmail = memberRepository.findByEmail(email);

        List<CardFormDto> cardFormDtoList = postRepository.findAllByOnlyMeFalseOrMemberOrderByRegTimeDesc(byEmail)
                .stream().map(CardFormDto::of).collect(Collectors.toList());

        for (CardFormDto cardFormDto : cardFormDtoList) {
            for (PostImg postImg : postImgList) {
                if (cardFormDto.getPostId().equals(postImg.getPost().getId())) {
                    cardFormDto.getPostImgDtoList().add(PostImgDto.of(postImg));
                }
            }
        }
        return cardFormDtoList;
    }
}
