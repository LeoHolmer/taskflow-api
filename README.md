# TaskFlow API 🚀

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![JWT](https://img.shields.io/badge/JWT-Authentication-red.svg)](https://jwt.io/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

A modern and scalable REST API for task and project management, built with Spring Boot 3.2.5 and following development best practices.

## 📋 Table of Contents

- [Features](#-features)
- [Technologies](#-technologies)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Usage](#-usage)
- [API Endpoints](#-api-endpoints)
- [Docker](#-docker)
- [Testing](#-testing)
- [Monitoring](#-monitoring)
- [Contributing](#-contributing)
- [License](#-license)

## ✨ Features

### 🔐 Security
- **JWT Authentication** with secure tokens
- **BCrypt encryption** for passwords
- **User roles** (USER, ADMIN)
- **CORS configured** for development and production

### 📊 Data Management
- **Soft Delete** for logical deletion
- **Complete pagination** on all listings
- **Robust validations** with custom messages
- **Automatic auditing** (createdAt, updatedAt)

### 🏗️ Architecture
- **Spring Boot 3.2.5** with Java 17
- **Clean Architecture** with well-defined layers
- **Lombok** for boilerplate reduction
- **Spring Data JPA** with Hibernate

### 📈 Monitoring and Observability
- **Spring Boot Actuator** for health checks
- **Micrometer** for performance metrics
- **Swagger/OpenAPI** for interactive documentation
- **Structured logging** with SLF4J

### 🐳 DevOps
- **Docker** and Docker Compose ready
- **Profiles** for development and production
- **H2** for development, **PostgreSQL** for production
- **Environment variables** for secure configuration

## 🛠️ Technologies

### Backend
- **Java 17** - Programming language
- **Spring Boot 3.2.5** - Main framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence
- **JWT** - Authentication tokens
- **Lombok** - Boilerplate code reduction

### Database
- **H2** - In-memory database (development)
- **PostgreSQL** - Relational database (production)
- **Hibernate** - ORM and migrations

### Documentation and Testing
- **Swagger/OpenAPI 3** - API documentation
- **Spring Boot Actuator** - Health checks and metrics
- **JUnit 5** - Unit testing
- **Mockito** - Testing mocks

### DevOps
- **Docker** - Containerization
- **Docker Compose** - Service orchestration
- **Maven** - Dependency management and build

## 📋 Prerequisites

Before you begin, make sure you have installed:

- **Java 17** or higher
- **Maven 3.6+** or use the included wrapper (`./mvnw`)
- **Docker** and Docker Compose (optional)
- **PostgreSQL** (production only)

## 🚀 Installation

### Option 1: Run Locally

1. **Clone the repository:**
   ```bash
   git clone https://github.com/LeoHolmer/taskflow-api.git
   cd taskflow-api
   ```

2. **Compile the project:**
   ```bash
   ./mvnw clean compile
   ```

3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Verify it works:**
   ```bash
   curl http://localhost:8080/actuator/health
   ```

### Option 2: Using Docker

1. **Build the image:**
   ```bash
   docker build -t taskflow-api .
   ```

2. **Run with Docker Compose:**
   ```bash
   docker-compose up -d
   ```

## ⚙️ Configuration

### Environment Variables

Create a `.env` file in the project root:

```env
# Database
DB_URL=jdbc:postgresql://localhost:5432/taskflow
DB_USERNAME=your_username
DB_PASSWORD=your_password

# JWT
JWT_SECRET=your_super_secret_jwt_key_here
JWT_EXPIRATION=86400000

# Application
APP_NAME=TaskFlow API
APP_VERSION=1.0.0
```

### Spring Profiles

- **`dev`** (default): Uses H2 in-memory database
- **`prod`**: Uses PostgreSQL and production configuration

To use a specific profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

## 📖 Usage

### Accessing the Application

Once running, the API will be available at:
- **API Base**: `http://localhost:8080`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **Health Check**: `http://localhost:8080/actuator/health`
- **H2 Console** (development): `http://localhost:8080/h2-console`

### Authentication

1. **User registration:**
   ```bash
   curl -X POST http://localhost:8080/auth/register \
     -H "Content-Type: application/json" \
     -d '{
       "name": "John Doe",
       "email": "john@example.com",
       "password": "password123"
     }'
   ```

2. **Login:**
   ```bash
   curl -X POST http://localhost:8080/auth/login \
     -H "Content-Type: application/json" \
     -d '{
       "email": "john@example.com",
       "password": "password123"
     }'
   ```

3. **Use the JWT token:**
   ```bash
   curl -H "Authorization: Bearer YOUR_JWT_TOKEN" \
        http://localhost:8080/api/users
   ```

## 🔗 API Endpoints

### Authentication
- `POST /auth/login` - Sign in
- `POST /auth/register` - Register user

### Users
- `GET /api/users` - List users (paginated)
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user (soft delete)

### Projects
- `GET /api/projects` - List projects (paginated)
- `POST /api/projects` - Create project
- `GET /api/projects/{id}` - Get project
- `PUT /api/projects/{id}` - Update project
- `DELETE /api/projects/{id}` - Delete project

### Tasks
- `GET /api/tasks` - List tasks (paginated)
- `POST /api/tasks` - Create task
- `GET /api/tasks/{id}` - Get task
- `PUT /api/tasks/{id}` - Update task
- `DELETE /api/tasks/{id}` - Delete task

### Monitoring
- `GET /actuator/health` - Health status
- `GET /actuator/info` - Application information
- `GET /actuator/metrics` - Performance metrics

## 🐳 Docker

### Run with Docker Compose

```bash
# Development (with H2)
docker-compose up -d

# Production (with PostgreSQL)
docker-compose -f docker-compose.prod.yml up -d
```

### Included Services

- **taskflow-api**: The Spring Boot application
- **postgres**: PostgreSQL database (production only)
- **pgadmin**: Web interface for PostgreSQL (optional)

## 🧪 Testing

### Run Tests

```bash
# All tests
./mvnw test

# Tests with coverage
./mvnw test jacoco:report

# Specific tests
./mvnw test -Dtest=UserServiceTest
```

### Included Tests

- **Unit Tests**: Services and utilities
- **Integration Tests**: Controllers and repositories
- **Security Tests**: Authentication and authorization

## 📊 Monitoring

### Health Checks

```bash
# General status
curl http://localhost:8080/actuator/health

# Database
curl http://localhost:8080/actuator/health/db

# Disk
curl http://localhost:8080/actuator/health/diskSpace
```

### Metrics

```bash
# All metrics
curl http://localhost:8080/actuator/metrics

# Specific metric
curl http://localhost:8080/actuator/metrics/jvm.memory.used
```

## 🤝 Contributing

Contributions are welcome! To contribute:

1. **Fork** the project
2. **Create** a feature branch (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. **Open** a Pull Request

### Development Guidelines

- Follow **SOLID** principles
- Write **tests** for new features
- Keep **documentation** updated
- Use **conventional commits**


## 👨‍💻 Author

**Leonardo Holmer**
- GitHub: [@LeoHolmer](https://github.com/LeoHolmer)
- LinkedIn: [Leonardo Holmer](https://linkedin.com/in/leonardoholmer)

---

⭐ If this project is helpful to you, give it a star!
