# Package Tracking

A Spring Boot microservice-based package tracking system for creating packages, tracking their lifecycle, and querying delivery status.

## Overview

This repository contains three Java services:

- `PackageService` – handles package creation, validation, and storage
- `TrackingService` – manages tracking updates and history
- `public-apikey` – provides public pincode lookup support used by the package service

The services are orchestrated using Docker Compose and rely on MySQL, Redis, and RabbitMQ.

## Project structure

```text
PackageTracking/
├── docker-compose.yaml
├── PackageService/
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   └── mvnw
├── TrackingService/
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   └── mvnw
├── public-apikey/
│   ├── src/
│   ├── pom.xml
│   └── mvnw
└── README.md
```

## Features

- Create and retrieve packages
- Track shipment status by tracking number
- Maintain package tracking history
- Validate pincode/post office integration
- Event-driven communication using RabbitMQ
- Local development via Docker Compose
- Swagger/OpenAPI documentation for REST APIs

## Architecture

```text
Client
  |
  v
PackageService (8081)
  |  \
  |   \-- publishes package/tracking events via RabbitMQ
  v
MySQL + Redis
  |
  v
TrackingService (8082)
  |  \
  |   \-- queries package data from PackageService
  v
Tracking history / status updates
```

## Services and endpoints

### PackageService
- Base URL: `http://localhost:8081`
- Swagger UI: `http://localhost:8081/swagger-ui.html`
- API base path: `/packages`

Main operations:
- `POST /packages` – create a package
- `GET /packages` – list packages
- `GET /packages/{trackingNumber}` – fetch a package

### TrackingService
- Base URL: `http://localhost:8082`
- Swagger UI: `http://localhost:8082/swagger-ui.html`
- API base path: `/tracking`

Main operations:
- `POST /tracking` – create tracking record
- `PUT /tracking/{trackingNumber}` – update tracking status
- `GET /tracking/{trackingNumber}` – get tracking details
- `GET /tracking/{trackingNumber}/history` – get history

## Prerequisites

- Java 17+
- Maven (or use included Maven wrapper scripts)
- Docker and Docker Compose
- MySQL, Redis, and RabbitMQ are provided through Docker Compose

## Run with Docker Compose

From the repository root:

```bash
docker compose up --build
```

This starts:
- MySQL on `localhost:3306`
- Redis on `localhost:6379`
- RabbitMQ on `localhost:5672` and management UI at `http://localhost:15672`
- phpMyAdmin at `http://localhost:8090`
- PackageService on `http://localhost:8081`
- TrackingService on `http://localhost:8082`

## Run services individually

### PackageService

```bash
cd PackageService
./mvnw spring-boot:run
```

### TrackingService

```bash
cd TrackingService
./mvnw spring-boot:run
```

### public-apikey

```bash
cd public-apikey
./mvnw spring-boot:run
```

## Configuration

The services use `application.yaml` files for configuration, including:
- datasource settings
- Redis host/port
- RabbitMQ host/port
- server ports
- Springdoc Swagger configuration

Environment variables used by Docker Compose:
- `MYSQL_HOST`
- `REDIS_HOST`
- `RABBITMQ_HOST`
- `PACKAGE_SERVICE_URL`

## Notes

- The project is built as a microservice example and is intended for local development and demonstration.
- RabbitMQ is used for event-based communication between package and tracking flows.
- MySQL initializes the required databases and supports persistent storage for package and tracking records.

## License

This project does not currently include a license file. Add one if you want to define usage and distribution rules.
