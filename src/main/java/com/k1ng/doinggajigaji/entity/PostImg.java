package com.k1ng.doinggajigaji.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "post_img")
@Getter
@Setter
public class PostImg extends BaseEntity{

    @Id
    @Column(name = "post_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void updatePostImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
        this.imgName = imgName;
    }
}
