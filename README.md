# Appointment History Service

Service responsible for consuming appointment events and persisting a full audit/history of each appointment over time. It listens to the fanout exchange published by Appointment Service, stores snapshots in PostgreSQL, and exposes GraphQL queries for searching and paging through history.

Part of the hospital_app microservices system.


## Tech stack
- Java 21, Spring Boot 3.5
- Spring Data JPA (PostgreSQL)
- Spring for GraphQL (GraphQL endpoint and GraphiQL/Voyager UIs)
- Spring Security (JWT resource server via shared jwt_security_common module)
- RabbitMQ consumer with TLS (JSON messages)
- Hexagonal architecture (ports and adapters)


## Architecture highlights
- Inbound adapters:
  - GraphQL controller for queries (search by appointmentId, by date range, past/future/all; pagination and filtering).
- Outbound adapters:
  - RabbitMQ listener that consumes AppointmentMessage events from the fanout exchange and persists them as AppointmentHistory records.
  - JPA repositories for persistence.
- Messaging topology:
  - Exchange: appointment.exchange (fanout)
  - Queue (bound to the exchange): history.queue
  - Payload: com.hospital_app.common.message.dto.AppointmentMessage (JSON)
- Security: Resource server expects a valid JWT. GraphiQL and Voyager are publicly accessible for convenience; actual data queries require authentication.


## Running locally
There are two common ways to run this service.

1) Using Docker Compose (recommended to run the whole system)
- Prerequisites: Docker and Docker Compose
- From the repo root, bring up dependencies and this service:
  docker compose up -d appointment-history-service appointment-history-service-db rabbitmq
- To actually receive events, run appointment-service as well (it publishes the messages):
  docker compose up -d appointment-service appointment-service-db
- Service base URL (container mapped): http://localhost:8000/history
- GraphQL endpoint: http://localhost:8000/history/graphql
- PostgreSQL (service DB): localhost:5436 (mapped to container 5432)

2) Running via Maven locally
- Prerequisites: JDK 21, Maven, a running PostgreSQL and RabbitMQ with TLS, and the Appointment Service to publish messages.
- Export env vars as in the root .env (see Environment variables) or create an application-local.yml.
- From the repo root (monorepo builds shared modules), run:
  mvn -q -DskipTests package
  mvn -q -pl appointment_history_service -am spring-boot:run
- Service runs on port 8080 by default; in Docker it is mapped to 8084.

Remote debugging
- If ENABLE_REMOTE_DEBUG=true in Docker, Java debug port 5008 is exposed.


## GraphQL API
Endpoint: POST /graphql

Interactive UIs:
- GraphiQL: /graphiql
- Voyager: /voyager

Available queries (see schema at src/main/resources/graphql/schema.graphqls):
- pastAppointmentHistoryByAppointmentId(id: UUID!, lastVersionOnly: Boolean = true, page: Int = 0, size: Int = 10)
- futureAppointmentHistoryByAppointmentId(id: UUID!, lastVersionOnly: Boolean = true, page: Int = 0, size: Int = 10)
- allAppointmentHistoryByAppointmentId(id: UUID!, lastVersionOnly: Boolean = true, page: Int = 0, size: Int = 10)
- pastAppointmentHistories(lastVersionOnly: Boolean = true, page: Int = 0, size: Int = 10, patientName: String, doctorName: String, status: String, startDate: Date!, endDate: Date!, patientEmail: String)
- futureAppointmentHistories(lastVersionOnly: Boolean = true, page: Int = 0, size: Int = 10, patientName: String, doctorName: String, status: String, startDate: Date!, endDate: Date!, patientEmail: String)
- allAppointmentHistories(lastVersionOnly: Boolean = true, page: Int = 0, size: Int = 10, patientName: String, doctorName: String, status: String, startDate: Date!, endDate: Date!, patientEmail: String)

Example query (allAppointmentHistoryByAppointmentId):
{
  allAppointmentHistoryByAppointmentId(id: "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee", lastVersionOnly: true, page: 0, size: 10) {
    pageInfo { pageNumber pageSize totalPages totalElements isFirst isLast }
    content {
      id appointmentId patientId patientEmail patientName doctorId doctorName status dateTime notes version
    }
  }
}

Note on access control:
- If the authenticated user has role PATIENT, results are automatically scoped to that patient (the controller reads user_id from JWT when role == PATIENT). Other roles (e.g., NURSE/DOCTOR/ADMIN) can query across patients per service rules.


## Messaging
- Exchange: appointment.exchange (fanout)
- Queue: history.queue
- Consumer: AppointmentNotificationQueueConsumerImpl listens to the queue and maps AppointmentMessage to domain AppointmentHistory via MessageAppointmentHistoryMapper, then persists.

Message fields (subset): id (appointmentId), patientId, patientEmail, patientName, doctorId, doctorName, status, dateTime, notes, version.

RabbitMQ uses TLS with keystore/truststore configured via env vars. See rabbitmq/ in repo root for cert materials and configuration.


## Persistence
- PostgreSQL via Spring Data JPA
- Entity: infra/adapter/out/db/jpa/appointment_history/JpaAppointmentHistoryEntity
- Repository layer provides filtering, paging, and past/future scoping.
- Schema is created/updated according to HOSPITAL_APP_APPOINTMENT_HISTORY_SERVICE_DB_DDL_AUTO (default update in .env).


## Security
- JWT resource server; most endpoints require authentication.
- GraphiQL and Voyager static resources are publicly accessible.
- The GraphQL controller enforces patient scoping for role PATIENT.

To test with JWTs locally, use a valid token matching the expected signing keys and roles (see jwt_security_common). Authorization header: Authorization: Bearer <token>.


## Environment variables
These are consumed by appointment_history_service (see application.yml and docker-compose.yml). Defaults are in the root .env file.

Database
- HOSPITAL_APP_APPOINTMENT_HISTORY_SERVICE_DB_URL=jdbc:postgresql://appointment-history-service-db:5432/appointment_history_service_db
- HOSPITAL_APP_APPOINTMENT_HISTORY_SERVICE_DB_USER=user
- HOSPITAL_APP_APPOINTMENT_HISTORY_SERVICE_DB_PASS=pass
- HOSPITAL_APP_APPOINTMENT_HISTORY_SERVICE_DB_DRIVER=org.postgresql.Driver
- HOSPITAL_APP_APPOINTMENT_HISTORY_SERVICE_DB_DDL_AUTO=update
- HOSPITAL_APP_APPOINTMENT_HISTORY_SERVICE_DB_DIALECT=org.hibernate.dialect.PostgreSQLDialect

RabbitMQ with TLS
- HOSPITAL_APP_RABBITMQ_HOST=rabbitmq
- HOSPITAL_APP_RABBITMQ_PORT=5671
- HOSPITAL_APP_RABBITMQ_USER=guest
- HOSPITAL_APP_RABBITMQ_PASS=guest
- HOSPITAL_APP_RABBITMQ_SSL_ENABLED=true
- HOSPITAL_APP_RABBITMQ_SSL_ALGO=TLSv1.2
- HOSPITAL_APP_RABBITMQ_SSL_VALIDATE=true
- HOSPITAL_APP_RABBITMQ_KEYSTORE=classpath:rabbitmq/client_keystore.p12
- HOSPITAL_APP_RABBITMQ_KEYSTORE_PASS=changeit
- HOSPITAL_APP_RABBITMQ_TRUSTSTORE=classpath:rabbitmq/truststore.p12
- HOSPITAL_APP_RABBITMQ_TRUSTSTORE_PASS=changeit

Other
- ENABLE_REMOTE_DEBUG=true (when running in Docker, exposes port 5008)


## Useful commands
- Build monorepo: mvn -q -DskipTests package
- Run service only: mvn -q -pl appointment_history_service -am spring-boot:run
- Start with dependencies via Docker:
  docker compose up -d appointment-history-service appointment-history-service-db rabbitmq appointment-service appointment-service-db
- View logs: docker logs -f hospital-app-appointment-history-service


## Troubleshooting
- RabbitMQ TLS errors: ensure keystore/truststore paths and passwords match packaged resources and .env
- No history appearing: confirm appointment-service is running and publishing, and that the exchange/queue are declared (they are by both services) and bound correctly
- DB connection failures: confirm appointment-history-service-db container is healthy and env vars are correct
- 401/403 responses: verify JWT token, roles, and that Authorization header is present


## Project paths and references
- Dockerfile: appointment_history_service/Dockerfile
- Spring config: appointment_history_service/src/main/resources/application.yml
- GraphQL schema: appointment_history_service/src/main/resources/graphql/schema.graphqls
- GraphQL controller: infra/adapter/in/controller/graphql/GraphQLAppointmentHistoryController.java
- RabbitMQ config: infra/config/message/rabbitmq/RabbitMQNotificationConfig.java
- Consumer: infra/adapter/out/message/AppointmentNotificationQueueConsumerImpl.java
- Persistence entity: infra/adapter/out/db/jpa/appointment_history/JpaAppointmentHistoryEntity.java
- Security config: infra/config/security/AppointmentHistoryConfiguration.java


## License
This project is part of an educational/portfolio repository. See root-level LICENSE if available.
