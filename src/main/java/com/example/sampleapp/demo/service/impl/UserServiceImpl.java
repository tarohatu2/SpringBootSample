package com.example.sampleapp.demo.service.impl;

import com.example.sampleapp.demo.entity.database.User;
import com.example.sampleapp.demo.entity.mapper.UserMapper;
import com.example.sampleapp.demo.entity.request.CreateUserRequest;
import com.example.sampleapp.demo.entity.response.CreateUserResponse;
import com.example.sampleapp.demo.entity.response.FindUserResponse;
import com.example.sampleapp.demo.entity.response.FindUserWithArticleResponse;
import com.example.sampleapp.demo.error.APIResponseError;
import com.example.sampleapp.demo.error.APIErrors;
import com.example.sampleapp.demo.repository.UserRepository;
import com.example.sampleapp.demo.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Autowired
    UserMapper mapper;

    @Transactional
    @Override
    public CreateUserResponse saveUser(CreateUserRequest user) {
        User newUser = mapper.map(user);
        User savedUser = repository.save(newUser);
        return mapper.map(savedUser);
    }

    @Override
    public FindUserWithArticleResponse findById(int id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new APIResponseError(APIErrors.NOT_FOUND, new IllegalArgumentException(), "指定したユーザーが存在しません");
        }
        return mapper.mapFindUserWithArticleResponse(user.get());
    }

    @Transactional
    @Override
    public List<FindUserResponse> findAll() {
        // streamを解放しない場合
        return repository.findByNameEndsWithOrderByIdDesc("Room2")
                .map(mapper::mapFindUserResponse)
                .collect(Collectors.toList());
        /*
        try (Stream<User> stream = repository.findByNameEndsWithOrderByIdDesc("Room2")) {
            return stream
                    .map(mapper::mapFindUserResponse)
                    .collect(Collectors.toList());
        }
         */
    }

    @Override
    public void queryUserByName(String name, Pageable pageable) {
        Page<User> users = repository.queryFirst10ByName(name, pageable);
        users.map(mapper::mapFindUserResponse).toList();
    }

    @Override
    public List<FindUserWithArticleResponse> findByIdWithArticle(int id) {
        List<User> user = repository.findUser(id);
        return user
                .stream()
                .map(mapper::mapFindUserWithArticleResponse)
                .collect(Collectors.toList());

    }

    @Override
    public Page<FindUserResponse> findAllUsers(int pageSize, int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return repository.findByOrderByNameDesc(pageRequest).map(mapper::mapFindUserResponse);
    }
}
