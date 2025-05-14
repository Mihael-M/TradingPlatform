# Crypto Trading Simulator Backend

This backend is a Java Spring Boot application designed to simulate cryptocurrency trading. It provides RESTful APIs for managing user accounts, crypto holdings, and transactions, while supporting real-time price updates.

## Overview

- **Language & Framework:** Java + Spring Boot
- **Architecture:** Layered (Controller → Service → Repository)
- **Database:** PostgreSQL (UUIDs, Enums, and Decimal fields)
- **Key Features:**
  - Buy/sell cryptocurrency transactions
  - Account and balance management
  - Crypto holdings tracking
  - Real-time price updates via WebSocket
  - Simple in-memory price caching

## Main Components

- **Controllers:** REST endpoints (e.g., `/account`, `/transactions`, `/holdings`)
- **Services:** Business logic (e.g., `AccountService`, `TransactionService`)
- **Repositories:** Data access with Spring JDBC
- **Entities/Models:** Map to database and domain logic
- **Converters:** Translate between entities and domain models
- **Configuration:** CORS and WebSocket setup

## API Highlights

- `GET /account` - Fetch account details
- `PUT /balance/{amount}` - Deposit or withdraw funds
- `GET /holdings` - List all crypto holdings
- `POST /transactions` - Execute a buy/sell operation
- `GET /api/cryptos` - Retrieve supported cryptocurrencies

## Setup Notes

- Predefined user: `admin@gmail.com` with initial balance `100000.0`
- WebSocket endpoint for prices: `/prices`
- Price source: Kraken API (partially implemented)

## License

This simulator is built for educational and demonstration purposes.
