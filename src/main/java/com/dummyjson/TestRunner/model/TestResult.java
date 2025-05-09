package com.dummyjson.TestRunner.model;

import lombok.Data;

import java.util.List;


@Data
public class TestResult {
    private String status;
    private TestSummary summary;
    private TestDetails details;
    private String message;

    @Data
    public static class TestSummary {
        private int totalScenarios;
        private int passedScenarios;
        private int failedScenarios;
        private String passRate;
        private String duration;
        private String startTime;
        private String endTime;
    }

    @Data
    public static class TestDetails {
        private List<Scenario> scenarios;

        @Data
        public static class Scenario {
            private String name;
            private String status;
            private List<Step> steps;
            private String error;

            @Data
            public static class Step {
                private String name;
                private String status;
                private String error;
            }
        }
    }
}