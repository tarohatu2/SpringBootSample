package com.example.sampleapp.demo.controller;

import com.example.sampleapp.demo.entity.request.CreateProfileRequest;
import com.example.sampleapp.demo.entity.response.CreateProfileResponse;
import com.example.sampleapp.demo.entity.response.FindProfileResponse;
import com.example.sampleapp.demo.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "profiles", produces = "application/json;charset=utf-8")
public class ProfileController {
    @Autowired
    ProfileService service;

    @Operation(summary = "新規プロフィール登録", description = "既存ユーザーにひもづくプロフィールを登録します")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "正常終了"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "バリデーションに失敗した場合"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "ユーザーが存在しない場合"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "すでに同一ユーザーに対してプロフィールが登録されている場合"
            )
    })
    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    CreateProfileResponse create(
            @PathVariable int userId,
            @RequestBody @Validated CreateProfileRequest request) {

        return service.create(userId, request);
    }

    @Operation(summary = "プロフィール一件検索", description = "idにひもづくプロフィールを取得します")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "正常終了"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "idが1未満の場合"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "プロフィールが存在しない場合"
            )
    })
    @GetMapping("/{id}")
    FindProfileResponse findById(
            @PathVariable @Min(1) int id
    ) {
        return service.findById(id);
    }
}
