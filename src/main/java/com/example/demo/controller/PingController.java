package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PingController {

    @GetMapping("/auth/ping")   // lo colgamos de /auth para que entre en tus reglas actuales
    public Map<String, Object> ping() {
        return Map.of(
                "pong", true,
                "ts", System.currentTimeMillis()
        );
    }
}