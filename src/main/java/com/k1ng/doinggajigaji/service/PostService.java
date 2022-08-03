package com.k1ng.doinggajigaji.service;

import com.k1ng.doinggajigaji.dto.CardFormDto;
import com.k1ng.doinggajigaji.dto.PostFormDto;
import com.k1ng.doinggajigaji.entity.Post;

import java.util.List;

public interface PostService {
    Long savePost(PostFormDto postFormDto, String email) throws Exception;
    List<CardFormDto> getAllCardForm();
    PostFormDto findPostById(Long postId);
    Long updatePost(PostFormDto postFormDto);
}
