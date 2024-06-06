package com.example.sampleapp.demo.service;

import com.example.sampleapp.demo.entity.request.CreateArticleRequest;
import com.example.sampleapp.demo.entity.response.CreateArticleResponse;
import com.example.sampleapp.demo.entity.response.FindArticleResponse;

public interface ArticleService {
    CreateArticleResponse create(CreateArticleRequest request);
    FindArticleResponse findById(int id);
}
