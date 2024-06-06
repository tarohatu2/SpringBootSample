package com.example.sampleapp.demo.entity.mapper;

import com.example.sampleapp.demo.entity.database.User;
import com.example.sampleapp.demo.entity.request.CreateUserRequest;
import com.example.sampleapp.demo.entity.response.CreateUserResponse;
import com.example.sampleapp.demo.entity.response.FindUserResponse;
import com.example.sampleapp.demo.entity.response.FindUserWithArticleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "name", source = "userName")
    User map(CreateUserRequest request);

    @Mapping(target = "userName", source = "name")
    CreateUserResponse map(User user);

    FindUserResponse mapFindUserResponse(User user);

    FindUserWithArticleResponse mapFindUserWithArticleResponse(User user);
}
