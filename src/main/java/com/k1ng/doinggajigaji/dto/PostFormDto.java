package com.k1ng.doinggajigaji.dto;

import com.k1ng.doinggajigaji.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostFormDto {

    private Long id;

    @NotBlank
    private String description;

    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private static ModelMapper modelMapper = new ModelMapper();

    public Post createPost(){
        return modelMapper.map(this, Post.class);
    }

    public static PostFormDto of(Post post) {
        return modelMapper.map(post, PostFormDto.class);
    }
}
