package com.k1ng.doinggajigaji.dto;

import com.k1ng.doinggajigaji.entity.Member;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class ProfileDto {

    private Long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nickName;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ProfileDto of(Member member) {
        return modelMapper.map(member, ProfileDto.class);
    }
}
