package com.danividalg.skillpillweb.service;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DemoService {
    private static final String HELLO = "Hello Dani World";
	
    @GetMapping("/hello")
    public String getHello() {
        return HELLO;
    }
}
