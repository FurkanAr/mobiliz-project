package com.mobiliz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping
    public String response(@RequestHeader("Authorization") String header) {
        System.out.println("Authorization " + header);
        return "Hello World";
    }
}
