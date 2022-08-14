package com.k1ng.doinggajigaji.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PasswordChangeDto {

    @Size(min = 8, max = 16, message = "8 ~ 16자 사이로 입력하세요.")
    private String inputPassword;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String currentPassword;

}
