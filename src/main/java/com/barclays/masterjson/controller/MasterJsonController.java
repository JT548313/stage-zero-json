package com.barclays.masterjson.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class MasterJsonController {
    @RequestMapping("/")
    public String moduleIndex() {
        return "Greetings from Spring Boot!";
    }

}