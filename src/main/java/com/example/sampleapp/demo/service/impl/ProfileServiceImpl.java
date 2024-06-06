package com.example.sampleapp.demo.service.impl;

import com.example.sampleapp.demo.entity.mapper.ProfileMapper;
import com.example.sampleapp.demo.entity.request.CreateProfileRequest;
import com.example.sampleapp.demo.entity.response.CreateProfileResponse;
import com.example.sampleapp.demo.error.APIResponseError;
import com.example.sampleapp.demo.error.APIErrors;
import com.example.sampleapp.demo.repository.ProfileRepository;
import com.example.sampleapp.demo.repository.UserRepository;
import com.example.sampleapp.demo.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    ProfileRepository repository;

    @Autowired
    ProfileMapper mapper;

    @Autowired
    UserRepository userRepository;

    /**
     * @param userId ユーザーId
     * @param request 登録用リクエストボディ
     * @return レスポンス
     */
    @Override
    public CreateProfileResponse create(int userId, CreateProfileRequest request) {
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new APIResponseError(APIErrors.NOT_FOUND, new IllegalArgumentException(), "指定されたユーザーが存在しません");
        }
        var profile = mapper.mapEntity(request);
        profile.setUser(user.get());
        try {
            return mapper.mapResponse(repository.save(profile));
        } catch (DataIntegrityViolationException ex) {
            throw new APIResponseError(APIErrors.CONFLICT, ex, "すでにプロフィールが登録されています");
        }
    }
}
