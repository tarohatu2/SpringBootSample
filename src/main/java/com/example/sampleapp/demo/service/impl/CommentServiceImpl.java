package com.example.sampleapp.demo.service.impl;

import com.example.sampleapp.demo.entity.mapper.CommentMapper;
import com.example.sampleapp.demo.entity.request.CreateCommentRequest;
import com.example.sampleapp.demo.entity.response.CreateCommentResponse;
import com.example.sampleapp.demo.entity.response.FindCommentResponse;
import com.example.sampleapp.demo.error.APIResponseError;
import com.example.sampleapp.demo.error.APIErrors;
import com.example.sampleapp.demo.repository.ArticleRepository;
import com.example.sampleapp.demo.repository.CommentRepository;
import com.example.sampleapp.demo.repository.UserRepository;
import com.example.sampleapp.demo.service.CommentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CommentMapper mapper;

    /**
     * 記事に対するコメントを新規に作成するメソッド
     * @param articleId 記事のId
     * @param body リクエストボディ
     * @return レスポンス
     */
    @Override
    @Transactional
    public CreateCommentResponse create(int articleId, CreateCommentRequest body) {
        var user = userRepository.findById(body.userId());
        var article = articleRepository.findById(articleId);

        if (user.isEmpty()) {
            throw new APIResponseError(APIErrors.NOT_FOUND, new IllegalArgumentException(), "指定されたユーザーが存在しません");
        }
        if (article.isEmpty()) {
            throw new APIResponseError(APIErrors.NOT_FOUND, new IllegalArgumentException(), "指定された記事が存在しません");
        }

        var comment = mapper.mapEntity(body);

        comment.setUser(user.get());
        comment.setArticle(article.get());

        return mapper.mapResponse(commentRepository.save(comment));
    }

    /**
     * @param commentId コメントId
     * @return レスポンス
     */
    @Override
    public FindCommentResponse findComment(Long commentId) {
        var comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new APIResponseError(APIErrors.NOT_FOUND, new IllegalArgumentException(), "指定されたコメントが存在しません");
        }

        return mapper.mapFindResponse(comment.get());
    }
}
