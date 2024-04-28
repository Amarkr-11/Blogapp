package com.blogapplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name= "post")
@Getter
@Setter
@NoArgsConstructor
public class Post  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name ="post_title", nullable = false)
    private String title;

    @Column(length = 1000)
    private String content;

    private String imageName;

    private Date addedDate;

    //making relation with user and category

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "cpost", cascade = CascadeType.ALL)
    private Set<Comment> comments=new HashSet<>();

}
