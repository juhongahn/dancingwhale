package com.k1ng.doinggajigaji.controller;

import com.k1ng.doinggajigaji.dto.DeletePostDto;
import com.k1ng.doinggajigaji.dto.PostFormDto;
import com.k1ng.doinggajigaji.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시물 생성
    
    @PostMapping("/new")
    public String posting(@Validated @ModelAttribute("post") PostFormDto postFormDto, BindingResult br,
                          Principal principal, Model model,
                          @RequestParam("itemImgFiles") List<MultipartFile> itemImgFileList) {
        log.info("PostFormDtd={}", postFormDto.toString());

        if (br.hasErrors()) {
            return "index";
        }
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

    @PreAuthorize("isAuthenticated() and ((#postFormDto.email == principal.username) or hasRole('ADMIN'))")
    @PostMapping("/{postId}/edit")
    public String postUpdate(@Validated PostFormDto postFormDto, BindingResult br,
            @RequestParam("postImgFile") List<MultipartFile> postImgFileList, Model model) {

        log.info("postFormDto={}",postFormDto);

        if (br.hasErrors()) {
            model.addAttribute("error", "수정에 실패했습니다.");
            return "index";
        }
        try {
            postService.updatePost(postFormDto, postImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "index";
        }
        return "redirect:/";
    }
    @ResponseBody
    @PreAuthorize("isAuthenticated() and ((#deletePostDto.email == principal.username) or hasRole('ADMIN'))")
    @PostMapping("/delete")
    public ResponseEntity<String> deletePost(DeletePostDto deletePostDto) {
        log.info("deletePostDto={}", deletePostDto);
        postService.deletePost(deletePostDto.getPostId());
        return new ResponseEntity<>("\"삭제됐습니다.\"", HttpStatus.OK);
    }
}
