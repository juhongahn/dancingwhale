package com.k1ng.doinggajigaji.post;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostFormDto {

    @NotBlank
    private String description;
}
