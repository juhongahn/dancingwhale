package com.k1ng.doinggajigaji.dto;

import com.k1ng.doinggajigaji.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardFormDto {

    private Long postId;

    private String nickName;

    private int likesCnt;

    private String description;

    private List<PostImgDto> postImgDtoList = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public static CardFormDto of(Post post) {
        CardFormDto cardFormDto = new CardFormDto();
        // 글 아이디
        cardFormDto.setPostId(post.getId());

        // 작성자 이름
        cardFormDto.setNickName(post.getMember().getNickName());

        // 좋아요 개수
        cardFormDto.setLikesCnt(post.getLikesList().size());

        // 글
        cardFormDto.setDescription(post.getDescription());

        return cardFormDto;
    }
}
