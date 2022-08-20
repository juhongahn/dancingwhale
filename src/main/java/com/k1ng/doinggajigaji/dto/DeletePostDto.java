package com.k1ng.doinggajigaji.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletePostDto {

    private Long postId;

    private Long memberId;
}
