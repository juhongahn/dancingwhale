package com.k1ng.doinggajigaji.entity;

import com.k1ng.doinggajigaji.dto.PostFormDto;
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
public class Post extends BaseEntity{

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 글이 삭제되면 좋아요도 다 날아가야지
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Likes> likesList = new ArrayList<>();


    public Post(String description) {
        this.description = description;
    }


    public void updatePost(PostFormDto postFormDto) {
        this.description = postFormDto.getDescription();
    }
}
