package com.example.sampleapp.demo.service;

import com.example.sampleapp.demo.entity.request.CreateCommentRequest;
import com.example.sampleapp.demo.entity.response.CreateCommentResponse;
import com.example.sampleapp.demo.entity.response.FindCommentResponse;

public interface CommentService {
    CreateCommentResponse create(int articleId, CreateCommentRequest body);

    FindCommentResponse findComment(Long commentId);
}
