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
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String email;

    private String nickName;

    private String password;

    private String status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    private boolean enabled;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    // 회원이 삭제되면 글도 모두 삭제됨.
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setNickName(memberFormDto.getNickName());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);
        member.setStatus("active");
        return member;
    }

    public void updateProfile(ProfileEditDto profileEditDto) {
        this.nickName = profileEditDto.getNickName();
        this.name = profileEditDto.getName();
        this.setUpdateTime(LocalDateTime.now());
    }

    public void updatePassword(PasswordChangeDto passwordChangeDto, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(passwordChangeDto.getConfirmPassword());
    }

    public void updatePassword(String newPassword, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(newPassword);
    }

}
