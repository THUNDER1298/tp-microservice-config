# Copilot Instructions for authorization-service

## Project Overview
This is a Spring Boot microservice for authorization, following standard Java/Spring conventions. The main entry point is `AuthorizationServiceApplication.java`. The service exposes REST endpoints via `AuthorizationController.java`.

## Architecture & Data Flow
- **Monolithic microservice**: All logic is contained in this service; no submodules.
- **Config Profiles**: Uses `application.properties` for both DEV and PROD profiles. DEV imports config from `http://localhost:8888`, PROD from `http://localhost:9888`.
- **Service Discovery**: Integrates with Eureka. DEV uses `http://localhost:8761/eureka/`, PROD uses `http://localhost:9761/eureka/`.
- **External Dependencies**: Relies on Spring Cloud Config Server and Eureka for configuration and service registration.

## Developer Workflows
- **Build**: Use `mvnw.cmd clean package` (Windows) or `./mvnw clean package` (Linux/macOS).
- **Run**: Use `mvnw.cmd spring-boot:run` or run `AuthorizationServiceApplication` from your IDE.
- **Test**: Run tests with `mvnw.cmd test`. Main test class: `AuthorizationServiceApplicationTests.java`.
- **Debug**: Standard Spring Boot debugging applies. No custom debug scripts.

## Project-Specific Conventions
- **Configuration**: All config is in `src/main/resources/application.properties`. Profile-specific overrides use `spring.config.on-profile`.
- **Package Structure**: Java code is under `com.example.authorizationservice` (main) and `com.example.authorization_service` (test).
- **No custom code generation or annotation processing** beyond standard Maven/Spring Boot.
- **No database integration** detected; service boundaries are REST and config only.

## Integration Points
- **Spring Cloud Config Server**: Required for config import. Service will start even if config server is unavailable (DEV profile uses `optional:` import).
- **Eureka**: Service registers itself for discovery. Ensure Eureka server is running before starting this service.

## Examples
- To switch to production profile, set `SPRING_PROFILES_ACTIVE=production` before running.
- To override config server or Eureka URLs, edit `application.properties`.

## Key Files
- `src/main/resources/application.properties`: All configuration.
- `src/main/java/com/example/authorizationservice/AuthorizationController.java`: REST endpoints.
- `src/main/java/com/example/authorizationservice/AuthorizationServiceApplication.java`: Main class.
- `src/test/java/com/example/authorization_service/AuthorizationServiceApplicationTests.java`: Main test class.

---
_If any conventions or workflows are unclear, please provide feedback to improve these instructions._
