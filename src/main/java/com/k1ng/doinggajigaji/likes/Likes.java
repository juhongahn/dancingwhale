package com.k1ng.doinggajigaji.likes;

import com.k1ng.doinggajigaji.member.Member;
import com.k1ng.doinggajigaji.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Likes(Member member, Post post) {
        this.member = member;
        this.post = post;
    }
}
