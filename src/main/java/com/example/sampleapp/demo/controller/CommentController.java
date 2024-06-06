package com.example.sampleapp.demo.controller;

import com.example.sampleapp.demo.entity.request.CreateCommentRequest;
import com.example.sampleapp.demo.entity.response.CreateCommentResponse;
import com.example.sampleapp.demo.entity.response.FindCommentResponse;
import com.example.sampleapp.demo.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "arts", produces = "application/json;charset=UTF-8")
public class CommentController {

    @Autowired
    CommentService service;

    @Operation(summary = "新規コメント登録", description = "指定の記事に対してコメントを投稿します")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "正常終了"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "指定したユーザーもしくは記事が見つからなかった場合"
            )
    })

    @PostMapping("/{articleId}/comments")
    public CreateCommentResponse createNewComment(@PathVariable int articleId, @RequestBody CreateCommentRequest body) {
        return service.create(articleId, body);
    }

    @Operation(summary = "コメント一件取得", description = "指定のIdでコメントを検索します")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "正常終了"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "指定したコメントが見つからなかった場合"
            )
    })
    @GetMapping("/{articleId}/comments/{id}")
    public FindCommentResponse findComment(@PathVariable int articleId, @PathVariable int id) {
        return service.findComment((long) id);
    }
}
