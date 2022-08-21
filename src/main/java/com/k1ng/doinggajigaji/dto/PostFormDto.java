package com.k1ng.doinggajigaji.dto;

import com.k1ng.doinggajigaji.entity.Post;
import com.k1ng.doinggajigaji.entity.PostImg;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostFormDto {

    private Long id;

    @NotBlank
    @Length(max = 301, message = "300자를 넘길수 없습니다.")
    private String description;

    private List<PostImgDto> postImgDtoList = new ArrayList<>();

    private List<Long> postImgIds = new ArrayList<>();

    private boolean onlyMe;

    private Long memberId;

    private static ModelMapper modelMapper = new ModelMapper();

    public Post createPost(){
        return modelMapper.map(this, Post.class);
    }

    public static PostFormDto of(Post post) {

        PostFormDto postFormDto = new PostFormDto();

        postFormDto.setId(postFormDto.getId());
        postFormDto.setDescription(post.getDescription());

        List<Long> imgIds = post.getPostImgList().stream().map(PostImg::getId).collect(Collectors.toList());
        postFormDto.setPostImgIds(imgIds);

        List<PostImgDto> imgDtoList = post.getPostImgList().stream().map(PostImgDto::of).collect(Collectors.toList());
        postFormDto.setPostImgDtoList(imgDtoList);

        postFormDto.setOnlyMe(post.isOnlyMe());

        postFormDto.setMemberId(post.getMember().getId());

        return postFormDto;
    }
}
