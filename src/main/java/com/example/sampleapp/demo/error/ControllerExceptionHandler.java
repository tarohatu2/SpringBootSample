package com.example.sampleapp.demo.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = APIResponseError.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseBody> handleAPIException(HttpServletRequest request, APIResponseError error) {
        logger.warn(error.getMessage());
        logger.warn(error.getStackTrace(","));
        // return new ResponseEntity<>(error.getError(), error.getError().getStatus());
        return new ResponseEntity<>(new ErrorResponseBody(error.getMessage()), error.getError().getStatus());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResponseEntity<HttpError> handleException(HttpServletRequest request, RuntimeException error) {
        logger.fatal(error.getStackTrace());
        HttpError internalServerError = APIErrors.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(internalServerError, internalServerError.getStatus());
    }
}
