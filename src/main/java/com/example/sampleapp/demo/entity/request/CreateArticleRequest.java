package com.example.sampleapp.demo.entity.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;

public record CreateArticleRequest(
        @NonNull String title,
        int userId) {

}
