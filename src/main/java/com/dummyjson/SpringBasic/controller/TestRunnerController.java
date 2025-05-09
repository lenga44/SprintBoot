package com.dummyjson.SpringBasic.controller;

import com.intuit.karate.Results;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tests")
public class TestRunnerController {

    @PostMapping("/run")
    public ResponseEntity<Map<String, Object>> runTest(@RequestParam String featurePath) {
        try {
            // Run the test
            Results results = com.intuit.karate.Runner.path("src/test/java/" + featurePath)
                    .parallel(1);
            
            // Convert results to map
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("featuresTotal", results.getFeaturesTotal());
            resultMap.put("featuresPassed", results.getFeaturesPassed());
            resultMap.put("featuresFailed", results.getFeaturesFailed());
            resultMap.put("scenariosTotal", results.getScenariosTotal());
            resultMap.put("scenariosPassed", results.getScenariosPassed());
            resultMap.put("scenariosFailed", results.getScenariosFailed());
            
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> listFeatures() {
        try {
            File testDir = new File("src/test/java");
            Map<String, Object> result = new HashMap<>();
            result.put("features", findFeatureFiles(testDir));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    private Map<String, String> findFeatureFiles(File directory) {
        Map<String, String> features = new HashMap<>();
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        features.putAll(findFeatureFiles(file));
                    } else if (file.getName().endsWith(".feature")) {
                        String relativePath = file.getPath()
                                .replace("src/test/java/", "")
                                .replace("\\", "/");
                        features.put(relativePath, file.getName());
                    }
                }
            }
        }
        return features;
    }
} 