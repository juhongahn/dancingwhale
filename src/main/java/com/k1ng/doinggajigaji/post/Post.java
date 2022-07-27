package com.k1ng.doinggajigaji.post;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "post")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private String description;


}
