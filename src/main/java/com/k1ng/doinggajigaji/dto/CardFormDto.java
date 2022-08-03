package com.k1ng.doinggajigaji.dto;

import com.k1ng.doinggajigaji.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardFormDto {

    private Long postId;

    private String nickName;

    private int likesCnt;

    private String description;

    public CardFormDto createCardForm(Post post) {
        CardFormDto cardFormDto = new CardFormDto();
        cardFormDto.setPostId(post.getId());
        cardFormDto.setNickName(post.getMember().getNickName());
        cardFormDto.setLikesCnt(post.getLikesList().size());
        cardFormDto.setDescription(post.getDescription());

        return cardFormDto;
    }
}
