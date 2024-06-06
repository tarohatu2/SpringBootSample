package com.example.sampleapp.demo.service;

import com.example.sampleapp.demo.entity.request.CreateProfileRequest;
import com.example.sampleapp.demo.entity.response.CreateProfileResponse;

public interface ProfileService {
    CreateProfileResponse create(int userId, CreateProfileRequest request);
}
