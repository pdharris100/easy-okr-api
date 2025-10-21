git# Easy OKR API

Easy OKR API is a Java-based RESTful service for managing Objectives and Key Results (OKRs) within organizations. It provides endpoints for handling domains, objectives, key results, teams, and organizations, supporting multi-tenancy and secure access.

## Features
- CRUD operations for Objectives, Key Results, Domains, Teams, and Organizations
- Multi-tenancy support via interceptors
- Custom exception handling for domain-specific errors
- Secure endpoints with configurable web security
- Extensible model and repository structure

## Technologies Used
- Java 11+
- Spring Boot
- Maven

## Project Structure
```
src/main/java/com/easyokr/
  Application.java                # Main entry point
  controller/                     # REST controllers
  deserialization/                # Custom deserializers
  exception/                      # Custom exceptions
  interceptor/                    # Multi-tenancy interceptors
  model/                          # Domain models
  repository/                     # Data repositories
  security/                       # Security configuration
  view/                           # View definitions
```

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven

### Build and Run
```bash
./mvnw clean install
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080` by default.

### Running Tests
```bash
./mvnw test
```

## API Endpoints
- `/domains` - Manage domains
- `/objectives` - Manage objectives
- `/keyresults` - Manage key results
- `/organisations` - Manage organizations
- `/teams` - Manage teams

Refer to controller classes for detailed endpoint documentation.

## Multi-Tenancy
Multi-tenancy is handled via the `MultiTenancyInterceptor` and related configuration. Tenants are validated for each request to ensure data isolation.

## Exception Handling
Custom exceptions provide clear error responses for not found and mismatch scenarios.

## Security
Endpoints are secured using Spring Security. Configuration is managed in `WebSecurityConfig.java`.

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request.

## License
This project is licensed under the MIT License.

## Contact
For questions or support, please contact the repository owner.
