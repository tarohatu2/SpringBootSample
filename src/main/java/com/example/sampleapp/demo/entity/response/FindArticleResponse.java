package com.example.sampleapp.demo.entity.response;

import java.util.Date;

public record FindArticleResponse(int id, String title, String userName, int userId, Date createdDateTime, Date updatedDateTime) {
}
