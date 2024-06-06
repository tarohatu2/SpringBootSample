package com.example.sampleapp.demo.entity.response;

import java.util.Date;

public record CreateCommentResponse(
        int id,
        String title,
        Date createdDate,
        Date updatedDate,
        int userId,
        int articleId
) {
}
