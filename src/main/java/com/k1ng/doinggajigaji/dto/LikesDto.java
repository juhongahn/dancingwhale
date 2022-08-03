package com.k1ng.doinggajigaji.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikesDto {

    @NotNull
    private Long postId;

    @NotNull
    private String email;
}
