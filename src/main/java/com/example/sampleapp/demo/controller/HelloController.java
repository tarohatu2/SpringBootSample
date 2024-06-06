package com.example.sampleapp.demo.controller;

import com.example.sampleapp.demo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {

    @Autowired
    private HelloService service;

    @GetMapping("/hello")
    public String getHello() {
        return service.getHelloMessage();
    }
}
