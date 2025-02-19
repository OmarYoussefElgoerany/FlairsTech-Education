package com.flairstech_education;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @CrossOrigin(origins = "http://localhost:4200") // Allow requests from this origin
    @GetMapping("hello")
    public String sayHello() {
        return "Hello, World!";
    }
}
