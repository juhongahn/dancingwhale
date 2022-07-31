package com.k1ng.doinggajigaji.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class PostFormDto {

    @NotBlank
    private String description;
}
