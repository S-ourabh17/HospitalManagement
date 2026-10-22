# Hospital Management System (HMS)

A backend REST API application built using **Java, Spring Boot, Spring Data JPA, and MySQL** to manage hospital operations, including patients, doctors, and appointments.

## Overview

The Hospital Management System provides RESTful APIs to manage patient records, doctor information, and appointment scheduling. It follows a layered architecture to separate controllers, business logic, and database operations, making the application easier to maintain and extend.

## Features

- **Patient Management:** Create, retrieve, update, and delete patient records.
- **Doctor Management:** Manage doctor information and records.
- **Appointment Management:** Create, retrieve, update, and delete appointments.
- **Appointment Status Management:** Update appointment status using a dedicated API.
- **JPA Relationships:** Manage relationships between patients, doctors, and appointments.
- **RESTful APIs:** Expose endpoints for HTTP-based communication.
- **Input Validation:** Validate incoming request data.
- **Database Integration:** Persist application data using MySQL and Spring Data JPA.
- **Layered Architecture:** Separate controller, service, repository, entity, DTO, and exception-handling responsibilities.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming |
| Spring Boot | Application framework |
| Spring Web | REST API development |
| Spring Data JPA | Database access and ORM |
| Hibernate | JPA implementation |
| MySQL | Relational database |
| Maven | Dependency management and build |
| Bean Validation | Request validation |
| Postman | API testing |
| Git and GitHub | Version control |

## Architecture

The application follows a layered architecture:

```text
Client / Postman
       |
       v
Controller Layer
       |
       v
Service Layer
       |
       v
Repository Layer
       |
       v
MySQL Database
```

### Layer Responsibilities

- **Controller:** Handles HTTP requests and responses.
- **Service:** Contains business logic and application rules.
- **Repository:** Performs database operations using Spring Data JPA.
- **Entity:** Represents database tables and relationships.
- **DTO / Request Objects:** Transfer data between the client and application.
- **Exception Handling:** Handles application errors and invalid requests.

## Project Structure

```text
HospitalManagement/
├── src/
│   ├── main/
│   │   ├── java/com/hospital/hms/
│   │   │   ├── controller/
│   │   │   ├── dtos/
│   │   │   ├── entity/
│   │   │   ├── enums/
│   │   │   ├── exception/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── HospitalManagementApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

*Note: Adjust the package and folder names to match your actual project structure.*

## Database Configuration

### 1. Create the database

Open MySQL and execute:

```sql
CREATE DATABASE hms_db;
```

### 2. Configure application properties

Add the following configuration to `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hms_db
spring.datasource.username=root
spring.datasource.password=300705

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Set the `DB_PASSWORD` environment variable to your MySQL password before running the application.

> **Security:** Do not commit database passwords or other credentials to GitHub. For production, use environment-specific configuration and a proper database migration tool.

## Getting Started

### Prerequisites

Install the following:

- JDK 17 or the Java version specified in `pom.xml`
- Maven (or use the Maven Wrapper if included)
- MySQL Server
- IntelliJ IDEA or another Java IDE
- Postman or a similar API client

