package com.k1ng.doinggajigaji.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PasswordChangeDto {

    @NotBlank
    @Size(min = 8, max = 16)
    private String inputPassword;

    @NotBlank
    @Size(min = 8, max = 16)
    private String confirmPassword;

    @NotBlank
    @Size(min = 8, max = 16)
    private String currentPassword;

}
