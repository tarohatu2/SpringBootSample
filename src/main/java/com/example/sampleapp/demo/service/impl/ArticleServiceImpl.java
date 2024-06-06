package com.example.sampleapp.demo.service.impl;

import com.example.sampleapp.demo.entity.database.Article;
import com.example.sampleapp.demo.entity.database.User;
import com.example.sampleapp.demo.entity.mapper.ArticleMapper;
import com.example.sampleapp.demo.entity.request.CreateArticleRequest;
import com.example.sampleapp.demo.entity.response.CreateArticleResponse;
import com.example.sampleapp.demo.entity.response.FindArticleResponse;
import com.example.sampleapp.demo.error.APIResponseError;
import com.example.sampleapp.demo.error.APIErrors;
import com.example.sampleapp.demo.repository.ArticleRepository;
import com.example.sampleapp.demo.repository.UserRepository;
import com.example.sampleapp.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleMapper mapper;


    @Override
    public CreateArticleResponse create(CreateArticleRequest request) {
        Article article = mapper.map(request);
        Optional<User> user = userRepository.findById(request.userId());
        if (user.isEmpty()) {
            throw new APIResponseError(APIErrors.NOT_FOUND, new IllegalArgumentException(), "指定されたユーザーが存在しません");
        }
        article.setUser(user.get());
        Article result = articleRepository.save(article);
        return mapper.map(result);
    }

    @Override
    public FindArticleResponse findById(int id) {
        Optional<Article> result = articleRepository.findById(id);

        if (result.isEmpty()) {
            throw new APIResponseError(APIErrors.NOT_FOUND, new IllegalArgumentException(), "指定された記事は存在しません");
        }

        return mapper.mapFindResponse(result.get());
    }
}
