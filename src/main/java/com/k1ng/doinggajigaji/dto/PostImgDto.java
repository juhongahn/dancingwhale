package com.k1ng.doinggajigaji.dto;

import com.k1ng.doinggajigaji.entity.PostImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class PostImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private static ModelMapper modelMapper = new ModelMapper();

    public static PostImgDto of(PostImg postImg) {
        return modelMapper.map(postImg, PostImgDto.class);
    }
}
