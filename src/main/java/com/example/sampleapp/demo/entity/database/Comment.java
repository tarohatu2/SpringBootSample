package com.example.sampleapp.demo.entity.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Comment")
public class Comment extends AbstractEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonBackReference("Article")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("User")
    private User user;


}
