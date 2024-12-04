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

### 2. Java Collection Framework

- **HashMap**: Used in the `ProductRepository` to cache products by their IDs. This allows for quick access to products, reducing lookup time. When a product is requested, it first checks the cache, and if the product is found, it is returned from the cache, improving performance. If the product is not found in the cache, it is loaded from the database and added to the cache for future use. [ProductRepository.java](api-service/src/main/java/com/vendoria/product/persistence/ProductRepository.java)

- **ArrayList**: Used in classes like `OrderItemService` to store lists of order items. This allows for dynamic resizing of the list and easy addition or removal of elements. [OrderItemService.java](api-service/src/main/java/com/vendoria/order/service/OrderItemService.java)

- **Stream API**: Utilized in various parts of the project for processing collections, such as filtering available products, converting lists of objects to DTOs, and other collection operations. [ProductDtoMapper.java](api-service/src/main/java/com/vendoria/product/mapper/ProductDtoMapper.java)

- **Optional**: Used for handling potential missing values, which helps avoid `NullPointerException` and makes the code safer. [OrderItemRepository.java](api-service/src/main/java/com/vendoria/order/persistence/OrderItemRepository.java)

### 3. Object class, Wrapper Classes and Generics

- Object methods overriding (equals, hashCode, clone): [BaseEntity.java](api-service/src/main/java/com/vendoria/common/entities/BaseEntity.java)
- Comparable interface implementation: [BaseEntity.java](api-service/src/main/java/com/vendoria/common/entities/BaseEntity.java)
- Generic classes and methods: [ResultWithValue.java](api-service/src/main/java/com/vendoria/common/ResultWithValue.java)
- Generic utility methods: [EntityUtils.java](api-service/src/main/java/com/vendoria/common/utils/EntityUtils.java)

### 4. String, StringBuffer, StringBuilder, Localization, Internationalization, and Date API

- **String**: Used for handling product names and descriptions. For example, the `Product` class has a method to format the product name. [Product.java](bff-service/src/main/java/com/vendoria/product/entity/Product.java)

- **StringBuffer**: Utilized in the `Order` class to build detailed order descriptions efficiently, allowing for thread-safe string manipulation. [Order.java](api-service/src/main/java/com/vendoria/order/entity/Order.java)
- **StringBuilder**: Used in the `OrderDtoMapper` to construct order details in a more efficient manner and now included in the `OrderDto`. [OrderDtoMapper.java](api-service/src/main/java/com/vendoria/order/mapper/OrderDtoMapper.java)
- **Date API**: The `Order` class uses `LocalDate` to store the order date, with methods to format them for display. [Order.java](api-service/src/main/java/com/vendoria/order/entity/Order.java)
- **Localization and Internationalization**: The project implements localization to support multiple languages, enhancing user experience. Localization is achieved through the use of `ResourceBundle` to manage language-specific properties files.

  - The `receipt.properties` file contains English localization keys, while `receipt_uk.properties` provides Ukrainian localization. The relevant code for generating localized receipts can be found in [ReceiptService.java](bff-service/src/main/java/com/vendoria/receipt/ReceiptService.java).
  
  - The receipt page template (`receipt.html`) utilizes Thymeleaf to dynamically display localized content based on the user's selected language. This allows users to interact with the application in their preferred language, improving accessibility and user satisfaction. The template can be found at [receipt.html](bff-service/src/main/resources/templates/receipt.html).

### 5. Exception Handling

- Custom exceptions: [ServiceException.java](bff-service/src/main/java/com/vendoria/exception/custom/ServiceException.java)
- Exception handling: [GlobalExceptionHandler.java](bff-service/src/main/java/com/vendoria/exception/handler/GlobalExceptionHandler.java)
- Exception logging: [CustomUserDetailsService.java](bff-service/src/main/java/com/vendoria/security/service/CustomUserDetailsService.java)

### 6. Lambda Expressions, Functional Interfaces, Method References, Stream API

- **Lambda Expressions**: Used in the `CartController` to process lists of `OrderItemDto` and convert them to `OrderItem`. [CartController.java](api-service/src/main/java/com/vendoria/order/controller/CartController.java)
- **Functional Interfaces**: The `ProductFilter` functional interface is used in the `ProductService` to filter products based on specific conditions. This allows for flexible filtering logic using lambda expressions. [ProductFilter.java](api-service/src/main/java/com/vendoria/product/filter/ProductFilter.java)

    #### Methods in ProductService

    - **filterProducts**: Filters a list of products based on a given `ProductFilter`. [ProductService.java](api-service/src/main/java/com/vendoria/product/service/ProductService.java)
        ```java
        public List<Product> filterProducts(List<Product> products, ProductFilter filter) {
            return products.stream()
                    .filter(filter::test)
                    .collect(Collectors.toList());
        }
        ```

    - **getAllProducts**: Retrieves all products and filters them based on stock quantity using the `filterProducts` method. [ProductService.java](api-service/src/main/java/com/vendoria/product/service/ProductService.java)
        ```java
        public ResultWithValue<List<ProductDto>> getAllProducts() {
            try {
                var productDtos = productDtoMapper
                        .mapProductListToProductDtoList(
                                filterProducts(
                                        productRepository.findAll(),
                                        product -> product.getStockQuantity() > 0
                                )
                        );

                return ResultWithValue.successWithValue(productDtos);
            } catch (Exception e) {
                log.error("Error getting all products", e);
                return ResultWithValue.failureWithResult(Error.UNEXPECTED);
            }
        }
        ```

    - **findByName**: Finds products by name and filters them based on stock quantity using the `filterProducts` method. [ProductService.java](api-service/src/main/java/com/vendoria/product/service/ProductService.java)
        ```java
        public ResultWithValue<List<ProductDto>> findByName(String name) {
            try {
                var productDtos = productDtoMapper
                        .mapProductListToProductDtoList(
                                filterProducts(
                                        productRepository.findByNameContainingIgnoreCase(name),
                                        product -> product.getStockQuantity() > 0
                                )
                        );

                return ResultWithValue.successWithValue(productDtos);
            } catch (Exception e) {
                log.error("Error getting all products", e);
                return ResultWithValue.failureWithResult(Error.UNEXPECTED);
            }
        }
        ```

- **Method References**: Method references can be used to simplify code in the `CartController` for converting DTOs to entities. [CartController.java](api-service/src/main/java/com/vendoria/order/controller/CartController.java)
- **Stream API**: Utilized in various parts of the project to process collections, such as filtering available products and calculating total order costs. [OrderDtoMapper.java](api-service/src/main/java/com/vendoria/order/mapper/OrderDtoMapper.java)

### 7. RegularExpressions, Reflection, Annotations

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