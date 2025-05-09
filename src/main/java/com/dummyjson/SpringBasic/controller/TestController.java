package com.dummyjson.SpringBasic.controller;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/hello")
    public Map<String, String> getHello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello World");
        return response;
    }

    @PostMapping("/echo")
    public Object postEcho(@RequestBody EchoRequest request) {
        return request;
    }

    @PutMapping("/update/{id}")
    public Map<String, Object> putUpdate(@PathVariable int id, @RequestBody UpdateRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("name", request.name);
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, String> deleteResource(@PathVariable int id) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Deleted successfully");
        return response;
    }

    public static class EchoRequest {
        public String text;
    }

    public static class UpdateRequest {
        public String name;
    }
} 