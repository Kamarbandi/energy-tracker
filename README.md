# Home Energy Tracker

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-green.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.1.0-blue.svg)](https://spring.io/projects/spring-cloud)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED.svg?logo=docker&logoColor=white)](https://docs.docker.com/compose/)
[![License](https://img.shields.io/badge/license-Educational-lightgrey.svg)]()

A **microservices reference implementation** for monitoring and reasoning about household electricity consumption. The system ingests energy readings from connected devices, processes them asynchronously, persists time-series metrics, raises alerts on usage spikes, and exposes a unified API through an **API Gateway** with built-in resilience, security, and observability.

---

## Project Overview

**Home Energy Tracker** simulates how a production system might collect **power (watts)** and **timestamps** from smart plugs or meters, aggregate that data for dashboards and billing-style views, and notify residents when consumption crosses defined thresholds.

**Problem it solves**

Raw device telemetry is high-volume and demands reliable ingestion, decoupled processing, and purpose-built storage — relational data for domain metadata, and time-series storage for measurements. This project demonstrates that separation of concerns using HTTP APIs for users and devices, Kafka for event streaming, InfluxDB for usage series, and MySQL for durable domain data.

**Typical use cases**

- Track **per-device** energy usage over time
- **Alert** when instantaneous or aggregated power exceeds a defined limit
- **Gate** all public HTTP traffic through a single entry point (API Gateway) with JWT validation
- **Observe** latency, error rates, and circuit-breaker state via Prometheus and Grafana

---

## Architecture Overview

The system follows a **microservices architecture** built primarily with **Spring Boot 4** and **Java 21**. Each service is independently deployable, with integration handled through **synchronous HTTP** (client → gateway → service) and **asynchronous messaging** (Kafka) wherever loose coupling and horizontal scale are required.

### Patterns and Capabilities

| Area | Approach |
|---|---|
| **API Gateway** | Spring Cloud Gateway (Server MVC); single public HTTP façade with route aggregation and OpenAPI aggregation |
| **Service Communication** | REST between the gateway and backend services; Kafka for the ingestion → usage → alerts pipeline |
| **Resilience** | **Circuit breakers** (Resilience4j) on gateway routes, with configured fallbacks |
| **Security** | **OAuth2 Resource Server** on the gateway; **Keycloak** for identity management (dev profile via Docker Compose) |
| **Observability** | Spring Boot **Actuator**, **Micrometer**, **Prometheus** scrape targets, and **Grafana** dashboards |
| **Configuration** | Per-service `application.properties` (no centralized Spring Cloud Config Server in this repository) |

### High-Level Interaction

Clients call the **API Gateway**, behind which sit the domain services — **user**, **device**, **ingestion**, and **insight**. The **ingestion** service publishes events to Kafka; **usage** consumes these events, writes them to **InfluxDB**, and may publish derived alerts; the **alert** service consumes those alerts and dispatches notifications (e.g., via **Mailpit** in local development). The **insight** service can provide AI-generated summaries (Spring AI), accessible through the gateway when enabled.

---