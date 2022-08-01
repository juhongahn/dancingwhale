package com.k1ng.doinggajigaji.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginFormDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
