package com.k1ng.doinggajigaji.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberFormDto {

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 8, max = 16, message = "8자에서 16자 사이로 입력해 주세요.")
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nickName;


}
