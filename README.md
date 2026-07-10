# i2i-Academy-RESTfulAPIDesign-14

# Customer API

A layered Spring Boot RESTful application that provides full CRUD operations for managing Customer data, integrates with a real Oracle relational database, and generates OpenAPI-compliant documentation using Swagger UI.

## Technologies

- Java 17
- Spring Boot 4.1.0
- Spring Data JPA (Hibernate)
- Oracle Database XE (running in Docker)
- springdoc-openapi (Swagger UI)
- Maven

## Architecture

The project follows a clean layered architecture:

- **Controller** – exposes REST endpoints, handles HTTP requests/responses
- **Service** – contains business logic, converts between Entities and DTOs
- **Repository** – Spring Data JPA interface for database access
- **Entity** – JPA-mapped class representing the `CUSTOMER` table
- **DTO** – Request/response objects used at the API boundary, keeping the database structure decoupled from the public API contract

## Getting Started

### 1. Start Oracle Database XE via Docker

```bash
docker run -d --name oracle-xe-i2i \
  -p 1522:1521 \
  -e ORACLE_PASSWORD=YourPassword123 \
  -e APP_USER=customer_user \
  -e APP_USER_PASSWORD=customer_pass \
  gvenzl/oracle-xe:21-slim
```

Wait until the container logs show `DATABASE IS READY TO USE!`.

### 2. Configure the application

Database connection settings are defined in `src/main/resources/application.yaml`. By default, the app connects to:

```
jdbc:oracle:thin:@localhost:1522/XEPDB1
```

with user `customer_user`. Update these values if your local setup differs.

### 3. Run the application

```bash
./mvnw spring-boot:run
```

On first run, Hibernate automatically creates the `CUSTOMER` table and the `CUSTOMER_SEQ` sequence used for primary key generation.

### 4. Access Swagger UI

Once the application is running, open:

```
http://localhost:8080/swagger-ui.html
```

All available endpoints can be explored and tested directly from this interface.

## API Endpoints

| Method | Endpoint               | Description                  |
|--------|-------------------------|-------------------------------|
| POST   | `/api/customers`        | Create a new customer         |
| GET    | `/api/customers/{id}`   | Retrieve a customer by ID     |
| GET    | `/api/customers`        | Retrieve all customers        |
| PUT    | `/api/customers/{id}`   | Update an existing customer   |
| DELETE | `/api/customers/{id}`   | Delete a customer             |

## Notes

- Primary keys are generated using a database `SEQUENCE` strategy rather than `IDENTITY`, for better compatibility with the Oracle JDBC driver.
- Request and response bodies use dedicated DTOs (`CustomerRequestDTO`, `CustomerResponseDTO`) instead of exposing the JPA entity directly, following REST API best practices.
- Input validation (e.g. required fields, valid email format) is enforced using Jakarta Bean Validation annotations.