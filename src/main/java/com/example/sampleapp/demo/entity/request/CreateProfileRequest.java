package com.example.sampleapp.demo.entity.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateProfileRequest(
        @NotNull @Pattern(regexp = "[a-zA-Z0-9]*")
        String nickname
) {
}
