package com.example.sampleapp.demo.entity.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

public record CreateCommentRequest(@NotBlank String title, int userId) {
}
