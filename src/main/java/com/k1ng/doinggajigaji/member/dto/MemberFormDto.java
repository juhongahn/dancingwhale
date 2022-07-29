package com.k1ng.doinggajigaji.member.dto;

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
    @Size(min = 8, max = 16)
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nickName;


}
