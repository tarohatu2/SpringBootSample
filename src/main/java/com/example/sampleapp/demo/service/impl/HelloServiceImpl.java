package com.example.sampleapp.demo.service.impl;

import com.example.sampleapp.demo.error.APIResponseError;
import com.example.sampleapp.demo.error.APIErrors;
import com.example.sampleapp.demo.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String getHelloMessage() {
        throw new APIResponseError(APIErrors.BAD_REQUEST, new IllegalAccessException(), "error");
        // return "hello world111";
    }
}
