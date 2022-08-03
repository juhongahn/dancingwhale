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
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(nullable = false)
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

    public void update(PostFormDto postFormDto) {
        this.description = postFormDto.getDescription();
    }
}
