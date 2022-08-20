package com.k1ng.doinggajigaji.controller;

import com.k1ng.doinggajigaji.dto.CardFormDto;
import com.k1ng.doinggajigaji.dto.DeletePostDto;
import com.k1ng.doinggajigaji.dto.PostFormDto;
import com.k1ng.doinggajigaji.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시물 생성
    @PostMapping("/new")
    public String posting(@ModelAttribute("post") PostFormDto postFormDto, Principal principal,
                          Model model, @RequestParam("itemImgFiles") List<MultipartFile> itemImgFileList) {

        log.info("PostFormDtd={}", postFormDto.toString());

        try {
            postService.savePost(postFormDto, principal.getName(), itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "글 등록 중 에러가 발생하였습니다.");
            return "index";
        }
        return "redirect:/";
    }

    // 게시물 수정
    @ResponseBody
    @GetMapping("/{postId}/edit")
    public ResponseEntity<PostFormDto> postUpdateForm(@PathVariable Long postId) {

        try {
            PostFormDto postFormDto = postService.getPostDtl(postId);
            return new ResponseEntity<>(postFormDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //@PreAuthorize("isAuthenticated() and ((@checker(#...)) or hasRole('ADMIN'))")
    @PostMapping("/{postId}/edit")
    @ResponseBody
    public ResponseEntity<Object> postUpdate(@RequestPart(value = "key") PostFormDto postFormDto,
                                              @RequestPart(value = "files", required = false) List<MultipartFile> postImgFileList,
                                              Model model) {
        log.info("postFormDto={}", postFormDto);
        if (postImgFileList != null )
            postImgFileList.forEach((postImgFile) -> log.info("postImgFile={}", postImgFile));
        else {
            log.info("postImgFile = null");
        }

        try {
            postService.updatePost(postFormDto, postImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("isAuthenticated() and (!@checker.isSelf(#deletePostDto.memberId) or hasRole('ADMIN'))")
    @PostMapping("/delete")
    public ResponseEntity<String> deletePost(DeletePostDto deletePostDto) {
        log.info("deletePostDto={}", deletePostDto);
        postService.deletePost(deletePostDto.getPostId());
        return new ResponseEntity<>("\"삭제됐습니다.\"", HttpStatus.OK);
    }

    @GetMapping(value = {"/my-posts"})
    public String viewMyPosts(Model model, Principal principal, @PageableDefault(size = 5) Pageable pageable) {

        Page<CardFormDto> allCardForm = postService.getAllPosts(principal.getName(), pageable);
        model.addAttribute("cards", allCardForm);
        String nlString = System.getProperty("line.separator");
        model.addAttribute("nlString", nlString);
        return "/post/myPosts";
    }
}
