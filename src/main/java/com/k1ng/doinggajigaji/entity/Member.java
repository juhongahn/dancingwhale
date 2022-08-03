package com.k1ng.doinggajigaji.entity;

import com.k1ng.doinggajigaji.constant.Role;
import com.k1ng.doinggajigaji.dto.MemberFormDto;
import com.k1ng.doinggajigaji.dto.PasswordChangeDto;
import com.k1ng.doinggajigaji.dto.ProfileEditDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String email;

    private String nickName;

    private String password;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    private String status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Post> postList = new ArrayList<>();

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setNickName(memberFormDto.getNickName());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);

        member.setRegTime(LocalDateTime.now());
        member.setUpdateTime(LocalDateTime.now());
        member.setStatus("active");
        return member;
    }

    public void updateProfile(ProfileEditDto profileEditDto){
        this.nickName = profileEditDto.getNickName();
        this.name = profileEditDto.getName();
        this.setUpdateTime(LocalDateTime.now());
    }

    public void updatePassword(PasswordChangeDto passwordChangeDto){
        this.password = passwordChangeDto.getConfirmPassword();
    }

}
