package com.example.sampleapp.demo.entity.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;

public record CreateUserRequest(
        @NonNull @NotEmpty String userName,
        @NonNull  String password
) { }
