# High-Concurrency Ticket Booking API

A robust, distributed REST API designed to process high-volume, simultaneous ticket reservations without encountering data race conditions, database deadlocks, or overselling. This system implements strict inventory management and asynchronous processing to optimize checkout workflows under heavy load.
## Features

  * Concurrency Control: Utilizes advanced database transaction management and pessimistic locking to ensure 100% strict ticket inventory accuracy during peak load events.

  * Distributed Caching: Integrates Redis to temporarily lock user reservations with a 5-minute Time-To-Live (TTL) window, reducing direct database reads and optimizing the checkout flow.

  * Asynchronous Message Queueing: Leverages RabbitMQ to decouple the order confirmation process. A background worker consumes messages to finalize ticket generation, keeping the main API threads highly responsive.

  * Infrastructure as Code: Fully containerized backend infrastructure using Docker and Docker Compose for seamless local development and production parity.
## Performance Benchmarks

The API was rigorously load-tested using Apache JMeter to validate the pessimistic locking and asynchronous queueing mechanisms.

 * Test Conditions: 500 concurrent user threads with a 1-second ramp-up time, generating an immediate traffic spike.(limited by local OS ephemeral port exhaustion, not system capacity).

 * Target: The Reserve Ticket and Confirm Purchase endpoints.
 * Throughput Achieved: Sustained ~485 requests per second during peak load.
  
 * Results:

   * 0.00% Error Rate across 1,000 total HTTP requests.

   * 4ms Average Response Time for ticket reservation (database locking + Redis caching).

   * 2ms Average Response Time for purchase confirmation.

* Bottleneck Mitigation: The integration of Redis TTL caching and RabbitMQ asynchronous processing entirely prevented database deadlocks. The system maintained single-digit millisecond response times despite the aggressive concurrency spike.

## Tech Stack

   * Language: Java

   * Framework: Spring Boot, Spring Data JPA

   * Database: PostgreSQL

   * Caching & State: Redis

   * Message Broker: RabbitMQ

   * Containerization: Docker, Docker Compose

   * Testing: JMeter (Load Testing)

## Prerequisites

  * Docker and Docker Compose installed.

  * Java Development Kit (JDK) installed.

  * Maven installed.

## Local Development Setup

The development environment utilizes Docker for the infrastructure (PostgreSQL, Redis, RabbitMQ) while allowing the Spring Boot application to run locally for rapid iteration.
### 1. Clone the repository
```bash
 git clone https://github.com/your-username/ticket-kai-backend.git
cd ticket-kai-backend
```
### 2. Start the infrastructure
Start the database, cache, and message broker in the background.
```bash
docker compose up -d ticket-postgres ticket-redis ticket-rabbitmq
```
### 3. Run the application
Run the Spring Boot application using the default local profile.
```bash
mvn spring-boot:run
```
The API will be accessible at http://localhost:8081. The RabbitMQ Management UI is accessible at http://localhost:15672 (Default credentials: guest / guest).
Load Testing (JMeter)

This system is built to withstand high concurrency. To verify performance, use the provided JMeter test plan in non-GUI mode to prevent client-side resource exhaustion.

### 1. Purge existing queues
Ensure the RabbitMQ ticketQueue is empty via the Management UI.

### 2. Reset testing data
Ensure the application starts with a clean database state (utilizing the automated TRUNCATE TABLE events CASCADE RESTART IDENTITY; configuration).

### 3. Execute the test
Start by navigating to the "load-testing" folder
```bash
cd load-testing
```
The execute the following
```bash
jmeter -n -t ticket-kai-plan.jmx -l results.csv -e -o ./report
```
## Deployment (Production VPS)
This application is designed to be deployed to a Virtual Private Server (VPS) such as a DigitalOcean Droplet running Linux.
### 1. Server Provisioning
Provision an Ubuntu server and install Docker and Docker Compose.
### 2. Clone the repository
Pull the codebase to your production server.
### 3. Configure Environment Variables
Create a .env file in the root directory to securely inject secrets into the containers. Do not commit this file to version control.
```bash
DB_PASSWORD=your_secure_postgres_password
RABBITMQ_DEFAULT_PASS=your_secure_rabbitmq_password

```
### 4. Start the Fleet
Execute the Docker Compose command to build the Spring Boot application container and launch the entire stack in detached mode.
```bash
docker compose up --build -d
```
