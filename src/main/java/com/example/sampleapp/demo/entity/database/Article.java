package com.example.sampleapp.demo.entity.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Article")
public class Article extends AbstractEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("User")
    private User user;
}
