# Vendoria - Java Learning Project

## Overview

Vendoria is an e-commerce educational project developed as part of a "Java Development" course. The project demonstrates practical application of various Java concepts and technologies.

## Project Purpose

The main goal is to implement and showcase core Java programming concepts including:

1. Java Basics (syntax, loops, conditions etc.)
2. Object-Oriented Programming
3. Object class, Wrapper classes, Generics, Object cloning and comparison
4. String manipulation, Localization, Date/Time API
5. Collections and Java Collections Framework
6. Exception handling and I/O Streams
7. Multithreading
8. Lambda expressions, Functional interfaces, Method references, Stream API
9. Regular expressions, Reflection, Annotations

## Architecture

The project uses a microservices architecture with three main components:

### BFF Service (Port 3000)

- Frontend-facing service
- Handles user interactions
- Manages authentication and authorization
- Uses Thymeleaf for server-side rendering

### API Service (Port 5000) 

- Core business logic service
- Interacts with PostgreSQL database
- Provides RESTful API endpoints
- Handles data persistence

### Eureka Server (Port 8761)

- Service discovery and registration
- Manages microservices communication

## Currently Implemented Topics

### 1. Object-Oriented Programming

- Abstract class implementation: [BaseEntity.java](api-service/src/main/java/com/vendoria/common/entities/BaseEntity.java)
- Interface implementation: [CustomUserDetails.java](bff-service/src/main/java/com/vendoria/security/entity/CustomUserDetails.java)
- Inheritance and method overriding: [User.java](api-service/src/main/java/com/vendoria/user/entity/User.java)

### 2. Exception Handling

- Custom exceptions: [ServiceException.java](bff-service/src/main/java/com/vendoria/exception/custom/ServiceException.java)
- Exception handling: [GlobalExceptionHandler.java](bff-service/src/main/java/com/vendoria/exception/handler/GlobalExceptionHandler.java)
- Exception logging: [CustomUserDetailsService.java](bff-service/src/main/java/com/vendoria/security/service/CustomUserDetailsService.java)

### 3. Object class, Wrapper Classes and Generics

- Object methods overriding (equals, hashCode, clone): [BaseEntity.java](api-service/src/main/java/com/vendoria/common/entities/BaseEntity.java)
- Comparable interface implementation: [BaseEntity.java](api-service/src/main/java/com/vendoria/common/entities/BaseEntity.java)
- Generic classes and methods: [ResultWithValue.java](api-service/src/main/java/com/vendoria/common/ResultWithValue.java)
- Generic utility methods: [EntityUtils.java](api-service/src/main/java/com/vendoria/common/utils/EntityUtils.java)

### 4. RegularExpressions, Reflection, Annotations

- Custom validation annotation: [Password.java](api-service/src/main/java/com/vendoria/common/validation/Password.java)
- Annotation processor (RegExp): [PasswordValidator.java](api-service/src/main/java/com/vendoria/common/validation/PasswordValidator.java)
- Validation constraints usage: [RegisterUserRequest.java](api-service/src/main/java/com/vendoria/user/requests/RegisterUserRequest.java)
- Spring annotations: [AuthController.java](api-service/src/main/java/com/vendoria/user/controller/AuthController.java)
- Custom reflection utilities: [ReflectionUtils.java](api-service/src/main/java/com/vendoria/common/utils/ReflectionUtils.java)

## Technologies Used

- Java 21
- Spring Boot 3.3.5
- Spring Cloud
- PostgreSQL
- Maven
- Docker
- Thymeleaf
- Tailwind CSS

## Getting Started

(To be added: setup instructions, prerequisites, and running instructions)