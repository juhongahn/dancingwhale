package com.k1ng.doinggajigaji.dto;

import com.k1ng.doinggajigaji.entity.Member;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;

@Data
public class ProfileEditDto {

    @NotBlank
    private String name;

    @NotBlank
    private String nickName;

    @NotBlank
    private String password;

    private String email;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ProfileEditDto of(Member member) {
        return modelMapper.map(member, ProfileEditDto.class);
    }
}
