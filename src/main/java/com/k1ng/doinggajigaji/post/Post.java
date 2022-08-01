package com.k1ng.doinggajigaji.post;

import com.k1ng.doinggajigaji.likes.Likes;
import com.k1ng.doinggajigaji.member.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    List<Likes> likesList = new ArrayList<>();


    public Post(String description) {
        this.description = description;
    }
}
