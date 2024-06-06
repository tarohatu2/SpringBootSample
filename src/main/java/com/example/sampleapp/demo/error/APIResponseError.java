package com.example.sampleapp.demo.error;

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class APIResponseError extends RuntimeException {
    @Getter
    private final HttpError error;
    @Getter
    private final Throwable cause;
    private final Object[] args;

    public APIResponseError(HttpError error, Throwable cause, String... args) {
        super();
        this.error = error;
        this.cause = cause;
        this.args = args;
    }

    public String getMessage() {
        return String.join(" ,", Arrays.stream(args)
                .map(Object::toString)
                .toList());
    }

    public String getStackTrace(String delimiter) {
        return String
                .join(delimiter, Arrays
                        .stream(this.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toList()
                );
    }
}
