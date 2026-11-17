# Spring Tool Suite - JWT Authentication

A minimal Spring Boot REST API with registration, BCrypt password hashing, login, JWT generation and a protected endpoint. It uses an in-memory H2 database so it can run without installing PostgreSQL.

## Requirements
- JDK 17 or later
- Spring Tool Suite 4 (or another Eclipse-based IDE)
- Internet connection for Maven to download dependencies

## Import and run in STS
1. Extract this ZIP file.
2. Open STS.
3. Select **File → Import → Maven → Existing Maven Projects**.
4. Browse to the extracted `sts-jwt-authentication` folder and select `pom.xml`.
5. Finish and wait for Maven dependencies to download.
6. Right-click `JwtAuthApplication.java` → **Run As → Spring Boot App**.
7. The server starts at `http://localhost:8080`.

## Test with Postman

### 1. Register
- Method: `POST`
- URL: `http://localhost:8080/api/auth/register`
- Body → raw → JSON:
```json
{
  "username": "student1",
  "email": "student1@example.com",
  "password": "pass123"
}
```

### 2. Login and receive JWT
- Method: `POST`
- URL: `http://localhost:8080/api/auth/login`
- Body → raw → JSON:
```json
{
  "username": "student1",
  "password": "pass123"
}
```
Copy the `token` value from the response.

### 3. Call protected endpoint
- Method: `GET`
- URL: `http://localhost:8080/api/profile`
- Authorization tab → **Bearer Token** → paste the token.
- Expected response:
```text
Hello student1! You accessed a protected endpoint using JWT.
```
Without a valid token, `/api/profile` returns HTTP 401.

## Notes
- Registration and login are public. All other API routes require a valid JWT.
- Tokens expire after 1 hour.
- H2 is in-memory: registered users are cleared when the application stops.
- The Base64 JWT secret in `application.properties` is for local learning only. Use a strong secret stored outside source control in production.
- To use PostgreSQL, replace the H2 dependency with `org.postgresql:postgresql`, then update datasource properties for your database.
