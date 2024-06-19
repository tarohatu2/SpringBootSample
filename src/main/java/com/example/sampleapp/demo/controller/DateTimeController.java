package com.example.sampleapp.demo.controller;

import com.example.sampleapp.demo.entity.response.GetDateResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "date")
public class DateTimeController {

    @GetMapping("/")
    public GetDateResponse getDate() {
        var date = new Date();
        return new GetDateResponse(date);
    }
}
