package com.k1ng.doinggajigaji.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginFormDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
