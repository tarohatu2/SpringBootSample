package com.example.sampleapp.demo.controller;

import com.example.sampleapp.demo.entity.request.CreateArticleRequest;
import com.example.sampleapp.demo.entity.response.CreateArticleResponse;
import com.example.sampleapp.demo.entity.response.FindArticleResponse;
import com.example.sampleapp.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    ArticleService service;

    @PostMapping("")
    public CreateArticleResponse create(@RequestBody CreateArticleRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public FindArticleResponse findById(@PathVariable int id) {
        return service.findById(id);
    }
}
