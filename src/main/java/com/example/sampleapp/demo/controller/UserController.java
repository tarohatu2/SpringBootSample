package com.example.sampleapp.demo.controller;

import com.example.sampleapp.demo.entity.database.User;
import com.example.sampleapp.demo.entity.request.CreateUserRequest;
import com.example.sampleapp.demo.entity.response.CreateUserResponse;
import com.example.sampleapp.demo.entity.response.FindUserResponse;
import com.example.sampleapp.demo.entity.response.FindUserWithArticleResponse;
import com.example.sampleapp.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService service;

    @GetMapping("")
    @Operation(summary = "ユーザー一覧取得", description = "ユーザーの一覧を取得します")
    public List<FindUserResponse> listUser() {
        return service.findAll();
    }

    @PostMapping("")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
        return service.saveUser(request);
    }


    @GetMapping("/{id}")
    @Operation(summary = "ユーザー個別検索", description = "ユーザーを個別に検索します")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "正常終了"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "ユーザーが見つからなかった場合"
            )
        }
    )
    public FindUserWithArticleResponse findUserById(@PathVariable int id) {
        return service.findById(id);
    }

    @GetMapping("/{id}/articles")
    public List<FindUserWithArticleResponse> findUserWithArticle(@PathVariable int id) {
        return service.findByIdWithArticle(id);
    }

    @GetMapping("/all")
    public Page<FindUserResponse> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return service.findAllUsers(size, page);
    }
}
