# Digital Coupon Marketplace

## Overview

This is a backend system for a digital coupon marketplace with the following features:

- Product management (Admin)
- pricing rules enforcement
- Secure reseller REST API
- Minimal frontend for admin and customer
- Fully Dockerized
- PostgreSQL database

## Architecture Decisions

- **Inheritance Strategy**: Used SINGLE_TABLE inheritance for Product and Coupon entities to optimize for simplicity since only one product type exists currently.
- **Atomic Purchases**: Purchases are handled with @Transactional and pessimistic locking to ensure only one buyer can purchase a coupon at a time.
- **Authentication**: Static Bearer token for reseller API.
- **Frontend**: Minimal Thymeleaf templates for admin and customer interfaces.

## Assumptions

- Only COUPON product type is implemented, but the system is designed for extensibility.
- Pricing is enforced server-side, cost_price and margin_percentage are not exposed in public APIs.
- Coupon value is only returned after successful purchase.

## How Atomic Purchase Works

To handle concurrent purchases:

1. Use @Transactional on the purchase method.
2. Use pessimistic locking (@Lock(LockModeType.PESSIMISTIC_WRITE)) on the repository.
3. Check if sold, if not, mark as sold and save.

This ensures that if two requests come simultaneously, one will wait for the lock, then see it's sold.

## How Pricing is Enforced

- minimum_sell_price is calculated as cost_price \* (1 + margin_percentage / 100)
- For resellers, reseller_price must be >= minimum_sell_price
- For direct customers, price is fixed to minimum_sell_price
- Pricing fields are not accepted from external input.

## How to Run Locally

### Prerequisites

- Java 17
- Maven
- docker

### Steps

1. Run `mvn clean install`
2. `docker-compose up --build`

3. Access:
    - Customer Frontend: http://localhost:8080/
    - Admin Frontend: http://localhost:8080/admin
    - Reseller API: http://localhost:8080/swagger-ui/index.html

## API Documentation

### Reseller Auth

- POST /api/v1/reseller/register - Register a reseller and receive an API token
- POST /api/v1/reseller/login - Authenticate and receive an existing API token

### Reseller API

need -> Headers: Authorization: Bearer <token from /api/v1/reseller/login>

- GET /api/v1/products - List available products
- GET /api/v1/products/{id} - Get product by ID
- POST /api/v1/products/{id}/purchase - Purchase product

### Admin API

- GET /api/admin/products - List products
- POST /api/admin/products - Create product

## Tradeoffs

- Used Thymeleaf for simplicity instead of a full JS framework.
- No pagination, for small scale.

## ToDo

- error handling improvement
- frontend expension
