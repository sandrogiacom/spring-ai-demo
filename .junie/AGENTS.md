# Developer Information

This document provides project-specific details to assist with development and debugging of the `spring-ai-demo` project.

## Build and Configuration

### Requirements
- **Java**: 25
- **Kotlin**: 2.3.0
- **Spring Boot**: 4.0.6
- **Spring AI**: 2.0.0-M8

### Project Setup
The project uses Maven. You can use the provided wrapper (`./mvnw`).

- **Compile**: `./mvnw clean compile`
- **Package**: `./mvnw clean package`
- **Run**: `./mvnw spring-boot:run`

### Infrastructure
The project includes `spring-boot-starter-data-jpa` and `postgresql` as a runtime dependency. Ensure a PostgreSQL instance is available and configured if running the full application context.

## Testing Information

### Configuration
Tests use **JUnit 5** with `kotlin-test-junit5`.

### Running Tests
- To run all tests: `./mvnw test`
- To run a specific test class: `./mvnw test -Dtest=ClassName`
- Using `run_test` tool (for Junie/AI agents): `run_test src/test/kotlin/path/to/Test.kt`

### Adding New Tests
- Place Kotlin tests in `src/test/kotlin`.
- For standard unit tests (no Spring context), create a class and use `@Test`.
- For tests requiring the Spring application context, use `@SpringBootTest`. Note: `@SpringBootTest` requires proper configuration of backing services like PostgreSQL.

### Test Example
A simple unit test demonstrating the process:

```kotlin
package com.sandrogiacom.spring_ai_demo

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SimpleUnitTest {
    @Test
    fun `simple addition works`() {
        val result = 2 + 2
        assertEquals(4, result)
    }
}
```

## Additional Development Information

### Kotlin Configuration
- The project is configured with `strict` JSR-305 checks for better null-safety when interacting with Java APIs.
- The `kotlin-maven-allopen` plugin is used to make classes annotated with `@Entity`, `@MappedSuperclass`, and `@Embeddable` open by default, as required by JPA.

### Dependencies
- **MCP Server**: Includes `spring-ai-starter-mcp-server-webmvc` for Model Context Protocol server capabilities.
- **JSON**: Uses Jackson with `jackson-module-kotlin`.
