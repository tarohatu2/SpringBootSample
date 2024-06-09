package com.example.sampleapp.demo.service;

import com.example.sampleapp.demo.entity.database.User;
import com.example.sampleapp.demo.entity.request.CreateUserRequest;
import com.example.sampleapp.demo.entity.response.CreateUserResponse;
import com.example.sampleapp.demo.entity.response.FindUserResponse;
import com.example.sampleapp.demo.entity.response.FindUserWithArticleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    CreateUserResponse saveUser(CreateUserRequest user);
    FindUserWithArticleResponse findById(int id);
    List<FindUserResponse> findAll();
    void queryUserByName(String name, Pageable pageable);
    List<FindUserWithArticleResponse> findByIdWithArticle(int id);

    Page<FindUserResponse> findAllUsers(int pageSize, int pageNumber);
}
