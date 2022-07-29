package com.k1ng.doinggajigaji.member;

import com.k1ng.doinggajigaji.member.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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


    public static Member formToMember(MemberFormDto memberFormDto) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setNickName(memberFormDto.getNickName());
        member.setPassword(memberFormDto.getPassword());

        return member;
    }

}
