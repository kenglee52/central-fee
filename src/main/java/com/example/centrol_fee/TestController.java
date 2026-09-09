package com.example.centrol_fee;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return " Hello! Spring Boot App is working!";
    }

    @GetMapping("/api/test")
    public String testApi() {
        return "API Connected successfully!";
    }
}