package com.batch.updater.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping("")
    public String hiTest() {
        System.out.println("hello test");
        return "hi";
    }
}