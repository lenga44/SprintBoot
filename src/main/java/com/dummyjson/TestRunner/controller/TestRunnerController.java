package com.dummyjson.TestRunner.controller;

import com.dummyjson.TestRunner.model.TestResult;
import com.dummyjson.TestRunner.service.TestExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/test")
public class TestRunnerController {

    private final TestExecutionService testExecutionService;

    @Autowired
    public TestRunnerController(TestExecutionService testExecutionService) {
        this.testExecutionService = testExecutionService;
    }

    @PostMapping("/run")
    public ResponseEntity<TestResult> runTest(@RequestParam("file") MultipartFile file) {
        TestResult result = testExecutionService.executeTest(file);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Test Runner is running");
    }
}