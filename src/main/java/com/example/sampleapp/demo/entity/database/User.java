package com.example.sampleapp.demo.entity.database;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Entity
@Table(name = "User")
public class User {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    private String name;

    @Setter
    private String password;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference("User")
    private List<Article> articles;
}
