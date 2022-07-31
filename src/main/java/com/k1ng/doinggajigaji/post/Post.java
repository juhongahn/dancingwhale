package com.k1ng.doinggajigaji.post;

import com.k1ng.doinggajigaji.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Post(String description) {
        this.description = description;
    }
}
