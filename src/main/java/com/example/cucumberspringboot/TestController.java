package com.example.cucumberspringboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @PostMapping("/test")
    public String sayHelloPost() {
        return "hello";
    }
}