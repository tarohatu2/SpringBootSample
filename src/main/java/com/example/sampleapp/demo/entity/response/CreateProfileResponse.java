package com.example.sampleapp.demo.entity.response;

public record CreateProfileResponse(
        int id,
        String nickname,
        int userId) {
}
