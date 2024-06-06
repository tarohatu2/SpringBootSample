package com.example.sampleapp.demo.entity.mapper;

import com.example.sampleapp.demo.entity.database.Comment;
import com.example.sampleapp.demo.entity.request.CreateCommentRequest;
import com.example.sampleapp.demo.entity.response.CreateCommentResponse;
import com.example.sampleapp.demo.entity.response.FindCommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    Comment mapEntity(CreateCommentRequest request);
    CreateCommentResponse mapResponse(Comment comment);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "articleId", source = "article.id")
    @Mapping(target = "articleTitle", source = "article.title")
    FindCommentResponse mapFindResponse(Comment comment);
}
