# SprintBoot
# Test Runner Project Structure

test-runner/                         # Thư mục gốc dự án
├── src/                             # Mã nguồn của dự án
│   ├── main/                        # Mã nguồn chính
│   │   ├── java/                    # Mã nguồn Java
│   │   │   └── com/example/testrunner/ # Package chính
│   │   │       ├── config/          # Cấu hình Spring Boot
│   │   │       ├── controller/      # Các controller xử lý HTTP requests
│   │   │       ├── model/           # Các lớp mô hình dữ liệu
│   │   │       ├── service/         # Các service xử lý logic
│   │   │       ├── util/            # Các tiện ích
│   │   │       └── TestRunnerApplication.java # Class chính khởi chạy ứng dụng
│   │   │
│   │   └── resources/               # Tài nguyên
│   │       ├── static/              # Tài nguyên tĩnh (CSS, JS, HTML)
│   │       ├── templates/           # Templates (nếu sử dụng Thymeleaf)
│   │       └── application.properties # Cấu hình ứng dụng
│   │
│   └── test/                        # Mã nguồn kiểm thử
│       ├── java/                    # Mã nguồn Java test
│       │   └── com/example/testrunner/
│       │       ├── steps/           # Cucumber step definitions
│       │       └── CucumberTestRunner.java # Cucumber runner
│       │
│       └── resources/               # Tài nguyên test
│           └── features/            # Cucumber feature files
│
├── uploads/                         # Thư mục lưu file upload
├── target/                          # Thư mục build (tự động tạo)
│   └── cucumber-report.json         # Báo cáo Cucumber
│
├── pom.xml                          # Cấu hình Maven
├── run.sh                           # Script chạy ứng dụng (Linux/Mac)
└── run.bat                          # Script chạy ứng dụng (Windows)

**Mô tả chi tiết**
**_Thư mục chính**_

**test-runner/**: Thư mục gốc chứa toàn bộ dự án
**src/**: Chứa tất cả mã nguồn
**pom.xml**: File cấu hình Maven, quản lý các dependencies và build
**run.sh/run.bat**: Script khởi chạy ứng dụng trên Linux/Mac và Windows

Mã nguồn chính (src/main)

**java/com/example/testrunner/**: Package chính của ứng dụng

**TestRunnerApplication.java**: Entry point của ứng dụng Spring Boot
**config/**: Các lớp cấu hình Spring Boot
**controller/**: Các REST controller xử lý HTTP requests
**model/**: Các entity và DTO
**service/**: Business logic
**util/**: Các class tiện ích


**resources/**: Các file tài nguyên không phải code

**static/**: File tĩnh như CSS, JavaScript, hình ảnh
**templates/**: Template HTML (Thymeleaf)
**application.properties**: Cấu hình ứng dụng Spring Boot



**Mã nguồn test (src/test)**

j**ava/com/example/testrunner/**: Package test

**steps/**: Cucumber step definitions
**CucumberTestRunner.java:** Cấu hình và chạy Cucumber tests


**resources/features/**: Cucumber feature files (.feature)

**Các thư mục khác**

**uploads/**: Thư mục lưu trữ file người dùng tải lên
**target/**: Thư mục chứa các file build, bao gồm báo cáo Cucumber