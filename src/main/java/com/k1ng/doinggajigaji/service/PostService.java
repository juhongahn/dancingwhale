package com.k1ng.doinggajigaji.service;

import com.k1ng.doinggajigaji.dto.CardFormDto;
import com.k1ng.doinggajigaji.dto.PostFormDto;
import com.k1ng.doinggajigaji.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    Long savePost(PostFormDto postFormDto, String email, List<MultipartFile> itemImgFileList) throws Exception;
    List<CardFormDto> getAllCardForm(String email);
    PostFormDto findPostById(Long postId);
    Long updatePost(PostFormDto postFormDto, List<MultipartFile> postImgFileList) throws IOException;
    PostFormDto getPostDtl(Long postId);
    void deletePost(Long postId);
}
