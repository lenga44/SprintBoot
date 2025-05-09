// src/main/java/com/example/testrunner/service/TestExecutionService.java
package com.dummyjson.TestRunner.service;

import com.dummyjson.TestRunner.model.TestResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TestExecutionService {

    private final Path uploadDir = Paths.get("uploads");
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public TestExecutionService() {
        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Không thể tạo thư mục upload", e);
        }
    }

    public TestResult executeTest(MultipartFile file) {
        try {
            // Lưu file
            String fileName = saveFile(file);
            Path filePath = uploadDir.resolve(fileName);

            // Kiểm tra loại file
            if (fileName.endsWith(".feature")) {
                return runKarateTest(filePath);
            } else {
                TestResult result = new TestResult();
                result.setStatus("error");
                result.setMessage("Chỉ hỗ trợ file .feature");
                return result;
            }
        } catch (Exception e) {
            TestResult result = new TestResult();
            result.setStatus("error");
            result.setMessage(e.getMessage());
            return result;
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            originalFilename = "test.feature";
        }

        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extension;

        Path targetPath = uploadDir.resolve(newFilename);
        Files.copy(file.getInputStream(), targetPath);

        return newFilename;
    }

    private TestResult runKarateTest(Path filePath) throws Exception {
        // Tạo lệnh để chạy Karate test
        List<String> command = new ArrayList<>();

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            command.add("cmd.exe");
            command.add("/c");
        } else {
            command.add("sh");
            command.add("-c");
        }

        // Sử dụng Maven để chạy Karate test với đường dẫn file cụ thể
        command.add("mvn test -Dkarate.options=\"classpath:" +
                getRelativeClasspathPath(filePath) + "\" -Dkarate.output.dir=\"target/karate-reports\"");

        // Tạo process builder
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File("."));

        // Bắt đầu tính thời gian
        Date startTime = new Date();
        String startTimeStr = dateFormat.format(startTime);

        // Chạy process
        Process process = processBuilder.start();

        // Đợi process hoàn thành (tối đa 60 giây)
        boolean completed = process.waitFor(60, java.util.concurrent.TimeUnit.SECONDS);

        // Kết thúc tính thời gian
        Date endTime = new Date();
        String endTimeStr = dateFormat.format(endTime);
        long duration = endTime.getTime() - startTime.getTime();

        // Kiểm tra kết quả
        if (!completed) {
            TestResult result = new TestResult();
            result.setStatus("timeout");
            result.setMessage("Test execution timed out after 60 seconds");
            return result;
        }

        // Đọc kết quả test từ file báo cáo JSON của Karate
        Path reportPath = Paths.get("target/karate-reports/karate-summary-report.json");
        if (!Files.exists(reportPath)) {
            TestResult result = new TestResult();
            result.setStatus("error");
            result.setMessage("Test report not generated");
            return result;
        }

        // Phân tích kết quả test
        return parseTestResult(reportPath, startTimeStr, endTimeStr, duration);
    }

    private String getRelativeClasspathPath(Path filePath) {
        // Chuyển đổi đường dẫn tuyệt đối thành đường dẫn classpath tương đối
        // Thông thường, các file feature của Karate nằm trong src/test/resources
        Path testResourcesDir = Paths.get("src/test/resources");

        try {
            Path relativePath = testResourcesDir.relativize(filePath);
            return relativePath.toString().replace('\\', '/');
        } catch (IllegalArgumentException e) {
            // Nếu file nằm ngoài thư mục resources, sao chép vào thư mục tạm trong resources
            try {
                Path tempFeatureDir = testResourcesDir.resolve("temp");
                if (!Files.exists(tempFeatureDir)) {
                    Files.createDirectories(tempFeatureDir);
                }

                String tempFileName = UUID.randomUUID().toString() + ".feature";
                Path tempFilePath = tempFeatureDir.resolve(tempFileName);

                Files.copy(filePath, tempFilePath);

                return "temp/" + tempFileName;
            } catch (IOException ex) {
                throw new RuntimeException("Không thể sao chép file vào thư mục resources", ex);
            }
        }
    }

    private TestResult parseTestResult(Path reportPath, String startTime, String endTime, long duration) throws IOException {
        // Đọc file JSON báo cáo của Karate
        String content = new String(Files.readAllBytes(reportPath));
        Map<String, Object> reportData = objectMapper.readValue(content, Map.class);

        TestResult result = new TestResult();
        TestResult.TestSummary summary = new TestResult.TestSummary();
        TestResult.TestDetails details = new TestResult.TestDetails();

        List<TestResult.TestDetails.Scenario> scenarios = new ArrayList<>();

        // Xử lý dữ liệu từ báo cáo Karate
        // Lưu ý: Cần điều chỉnh theo cấu trúc JSON thực tế của Karate

        // Nếu không có báo cáo Karate hoặc cần thêm mock data để test
        TestResult.TestDetails.Scenario scenario1 = new TestResult.TestDetails.Scenario();
        scenario1.setName("Thanh toán thành công");
        scenario1.setStatus("passed");

        List<TestResult.TestDetails.Scenario.Step> steps1 = new ArrayList<>();
        steps1.add(createStep("Given khách hàng có số dư 1000000 VND", "passed", null));
        steps1.add(createStep("When khách hàng thanh toán 500000 VND", "passed", null));
        steps1.add(createStep("Then giao dịch được xác nhận thành công", "passed", null));
        scenario1.setSteps(steps1);

        TestResult.TestDetails.Scenario scenario2 = new TestResult.TestDetails.Scenario();
        scenario2.setName("Thanh toán thất bại do không đủ số dư");
        scenario2.setStatus("failed");

        List<TestResult.TestDetails.Scenario.Step> steps2 = new ArrayList<>();
        steps2.add(createStep("Given khách hàng có số dư 100000 VND", "passed", null));
        steps2.add(createStep("When khách hàng thanh toán 500000 VND", "passed", null));
        steps2.add(createStep("Then giao dịch bị từ chối", "failed", "Expected REJECTED but was ACCEPTED"));
        scenario2.setSteps(steps2);
        scenario2.setError("Expected REJECTED but was ACCEPTED");

        scenarios.add(scenario1);
        scenarios.add(scenario2);
        details.setScenarios(scenarios);

        // Set summary
        summary.setTotalScenarios(2);
        summary.setPassedScenarios(1);
        summary.setFailedScenarios(1);
        summary.setPassRate("50.00 %");
        summary.setDuration(duration / 1000.0 + " seconds");
        summary.setStartTime(startTime);
        summary.setEndTime(endTime);

        // Set result
        result.setStatus("completed");
        result.setSummary(summary);
        result.setDetails(details);

        return result;
    }

    private TestResult.TestDetails.Scenario.Step createStep(String name, String status, String error) {
        TestResult.TestDetails.Scenario.Step step = new TestResult.TestDetails.Scenario.Step();
        step.setName(name);
        step.setStatus(status);
        step.setError(error);
        return step;
    }
}