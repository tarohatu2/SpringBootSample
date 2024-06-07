package com.example.sampleapp.demo.service;

import com.example.sampleapp.demo.entity.request.CreateProfileRequest;
import com.example.sampleapp.demo.entity.response.CreateProfileResponse;
import com.example.sampleapp.demo.entity.response.FindProfileResponse;

public interface ProfileService {
    CreateProfileResponse create(int userId, CreateProfileRequest request);
    FindProfileResponse findById(int profileId);
}
