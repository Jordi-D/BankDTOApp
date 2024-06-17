# BankAppDTO Application

BankAppDTO is a Java-based web application developed using Spring Boot for implementing CRUD operations related to banking functionalities.

## Overview

BankAppDTO facilitates managing various aspects of banking operations including loans, cards, and accounts. It provides RESTful APIs for creating, fetching, updating, and deleting data related to these entities.

## Technologies Used

- [Java](https://www.java.com/en/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [H2 Database (in-memory)](https://www.h2database.com/html/main.html)
- [Swagger/OpenAPI for API documentation](https://swagger.io/)
- [Lombok](https://projectlombok.org/) for reducing boilerplate code
- [Jakarta Bean Validation](https://beanvalidation.org/) for input validation

## Features

- **Loans Management:** APIs to handle loan creation, retrieval, update, and deletion.
- **Cards Management:** APIs for managing customer card details.
- **Accounts Management:** APIs to manage customer account details.
- **AuditAware Integration:** Utilizes AuditAware to automatically capture and store auditing information (created by, last modified by, etc.) for entities.
- **DTOs for Design:** The application uses Data Transfer Objects (DTOs) extensively to encapsulate data transferred over the network, ensuring separation of concerns and promoting maintainability.

## Security

The application is secured using Spring Security to protect sensitive operations and data access. Access to APIs may be restricted based on user roles and permissions.

## API Documentation

Explore the API endpoints and operations using Swagger UI:
- Access Swagger UI at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) once the application is running.

## Project Status

Status: In Development

## Contact Information

For any inquiries or support related to BankAppDTO, please contact:
- Email: devjordi.jd97@gmail.com
- LinkedIn: [Jordi Diéguez Sánchez](https://www.linkedin.com/in/jordi-dieguez-sanchez/)
