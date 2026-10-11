# E-commerce Product Service

A Spring Boot REST API for managing e-commerce products using MongoDB, with JWT-based authentication and authorization.

## Technologies Used

- Java
- Spring Boot
- Spring Data MongoDB
- Spring Security
- JWT Authentication
- ModelMapper
- Maven

## Features

- User registration and login
- Password encryption using BCrypt
- JWT access and refresh token generation
- Product management through REST APIs
- Role-based access control for CUSTOMER and ADMIN
- Centralized exception handling

## Prerequisites

- JDK 21
- MongoDB
- Maven (or the included Maven Wrapper)

## Configuration

Configure MongoDB in `src/main/resources/application.yaml`.

Set the JWT secret as an environment variable before running the application.

**PowerShell:**

```powershell
$env:JWT_SECRET = "local-development-secret"
```

Use a strong, private secret. Never commit real secrets or passwords to GitHub.

## Run the Application

From the project directory, run:

```powershell
.\mvnw.cmd spring-boot:run
```

The application runs on `http://localhost:8080` by default.

## API Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/auth/signup` | Register a user |
| POST | `/api/auth/login` | Log in |
| GET | `/api/products` | Retrieve products |

Product creation and other product operations require the appropriate authorization.

## Project Status

Developed as a Spring Boot and MongoDB e-commerce service project.
