package com.example.sampleapp.demo.entity.response;

import com.example.sampleapp.demo.entity.database.Article;

import java.util.Date;

public record FindCommentResponse(
        int id,
        String title,
        String userName,
        int userId,
        Date createdDateTime,
        Date updatedDateTime,
        int articleId,
        String articleTitle
) {
}
