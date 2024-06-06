package com.example.sampleapp.demo.entity.response;

import com.example.sampleapp.demo.entity.database.Article;

import java.util.List;

public record FindUserWithArticleResponse(int id, String name, List<Article> articles) {
}
