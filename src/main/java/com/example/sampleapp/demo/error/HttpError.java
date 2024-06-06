package com.example.sampleapp.demo.error;

import org.springframework.http.HttpStatus;

public interface HttpError {
    HttpStatus getStatus();
    String getMessage();
    String name();
}
