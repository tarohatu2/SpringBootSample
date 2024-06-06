package com.example.sampleapp.demo.error;

import org.springframework.http.HttpStatus;

public enum APIErrors implements HttpError {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "リクエストが不正です"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "認証に失敗しました"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "権限がありません"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "存在しません"),
    CONFLICT(HttpStatus.CONFLICT, "すでに登録されています"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "不明なエラー")
    ;

    private final HttpStatus status;
    private final String message;

    private APIErrors(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
