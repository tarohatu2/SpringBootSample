package com.example.sampleapp.demo.entity.mapper;

import com.example.sampleapp.demo.entity.database.Article;
import com.example.sampleapp.demo.entity.request.CreateArticleRequest;
import com.example.sampleapp.demo.entity.response.CreateArticleResponse;
import com.example.sampleapp.demo.entity.response.FindArticleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleMapper {
    Article map(CreateArticleRequest request);

    @Mapping(target = "userId", source = "user.id")
    CreateArticleResponse map(Article article);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    FindArticleResponse mapFindResponse(Article article);
}
