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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
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
    private final FileService fileService;

    @Value("${upload.postImgLocation}")
    private String postImgLocation;

    @Override
    public Long savePost(PostFormDto postFormDto, String email, List<MultipartFile> postImgFileList) throws Exception {

        // 게시물 등록
        Post post = postFormDto.createPost();
        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        post.setMember(member);
        postRepository.save(post);

        
        // ""인 파일이 있다면 제거
        postImgFileList.stream()
                .filter(multipartFile -> Objects.equals(multipartFile.getOriginalFilename(), ""))
                .collect(Collectors.toList())
                .forEach(postImgFileList::remove);

        for (MultipartFile multipartFile : postImgFileList) {
            PostImg postImg = new PostImg();
            postImg.setPost(post);
            postImgService.savePostImg(postImg, multipartFile);
        }

        return post.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public PostFormDto findPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        return PostFormDto.of(post);
    }

    private Long updatePostIfDeleted(Post post, List<Long> postImgIds) throws IOException {

        List<Long> savedPostImgIds = post.getPostImgList().stream()
                .map(PostImg::getId).collect(Collectors.toList());

        List<Long> notMatchIds = savedPostImgIds.stream().filter((postImgId) -> postImgIds.stream().noneMatch(
                Predicate.isEqual(postImgId)
        )).collect(Collectors.toList());

        // 이미지 삭제
        for (Long notMatchId : notMatchIds) {
            log.info("notMatchId={}", notMatchId);
            PostImg postImg = postImgRepository.findById(notMatchId)
                    .orElseThrow(EntityNotFoundException::new);

            postImgRepository.delete(postImg);

            fileService.deleteFile(postImgLocation+"/"+
                    postImg.getImgName());
            post.getPostImgList().remove(postImg);
        }

        return post.getId();
    }

    /**
     * 이미지 업데이트에는 2가지 경우가있다.
     *  1) 기존에 있던 이미지는 그대로이거나 삭제된 경우,
     *  2) 새로운 이미지만 추가되는 경우(일부러 이미지를 추가 할 때는 모든 이미지가 날라가게 했다.)
     * @param postFormDto
     * @param postImgFileList
     * @return
     * @throws IOException
     */

    @Override
    public Long updatePost(PostFormDto postFormDto, List<MultipartFile> postImgFileList) throws IOException {

        Post post = postRepository.findById(postFormDto.getId()).orElseThrow(EntityNotFoundException::new);

        // 이미지 이 외 수정사항들
        post.updatePost(postFormDto);

        List<Long> postImgIds = postFormDto.getPostImgIds();
        log.info("postImgIds={}",  postImgIds.toString());

        // 기존 이미지를 삭제했을 때
        if (postImgFileList == null) {
            updatePostIfDeleted(post, postImgIds);
            
        // 새로운 이미지를 추가할 때 (기존 이미지들 전부 제거해야함)
        } else {
            //먼저 db에서 제거후
            postImgRepository.deleteAllByPostId(post.getId());
            post.getPostImgList().clear();


            // 저장소에서 제거
            post.getPostImgList().forEach(postImg ->
                fileService.deleteFile(postImgLocation+"/"+ postImg.getImgName()));

            // 새로운 이미지 추가.
            for (MultipartFile multipartFile : postImgFileList) {
                PostImg postImg = new PostImg();
                postImg.setPost(post);
                postImgService.savePostImg(postImg, multipartFile);
            }
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

        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        // 이미지도 같이 삭제해준다.
        post.getPostImgList().forEach(postImg -> fileService
                .deleteFile(postImgLocation+"/"+ postImg.getImgName()));
        postRepository.delete(post);

    }

    @Override
    public Page<CardFormDto> getAllPosts(String email, Pageable pageable) {

        Member member = memberRepository.findByEmail(email);
        Page<Post> allPagePosts = postRepository.findAllByMemberOrderByRegTimeDesc(member, pageable);
        List<Post> allPosts = allPagePosts.getContent();

        Page<CardFormDto> cardFormDtoList = allPagePosts.map(CardFormDto::of);

        List<PostImg> postImgList = new ArrayList<>();

        allPosts.forEach(post -> postImgList
                        .addAll(postImgRepository.findByPostIdOrderByIdAsc(post.getId()))
                );


        for (CardFormDto cardFormDto : cardFormDtoList) {
            for (PostImg postImg : postImgList) {
                if (cardFormDto.getPostId().equals(postImg.getPost().getId())) {
                    cardFormDto.getPostImgDtoList().add(PostImgDto.of(postImg));
                }
            }
        }
        return cardFormDtoList;
    }


    @Transactional(readOnly = true)
    @Override
    public Page<CardFormDto> getAllCardForm(String email, Pageable pageable) {

        List<PostImg> postImgList = postImgRepository.findAllByOrderByIdAsc();

        Member member = memberRepository.findByEmail(email);

        Page<CardFormDto> cardFormDtoList = postRepository.
                findAllByOnlyMeFalseOrMemberOrderByRegTimeDesc(member, pageable).map(CardFormDto::of);


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
