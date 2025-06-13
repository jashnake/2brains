# Employee Management API

## Overview
REST API service for employee management built with Spring Boot. 
The API provides a set of endpoints for handling employee data with validations, error handling, and documentation.

## Technologies
- Java 11
- Spring Boot 3.4.3
- Spring Data JPA
- MapStruct
- Maven
- Swagger/OpenAPI
- JUnit 5 & Mockito

## Features
- Operations for employee management
- DTO pattern implementation
- Input validation and error handling
- Comprehensive API documentation with Swagger
- Unit and integration testing
- Structured logging system


## Prerequisites
- JDK 11+
- Maven 3.8+

## Branching Strategy
* We have a develop branch that is the main one
* To make changes, create a branch from develop and name it feature/{feature number}

## Installation & Setup

1. Clone the repository:
   git clone https://github.com/jashnake/2brains.git

2.  cd 2brains

3.  cd employee-manager

4.  git checkout develop

5.  mvn clean install

6.  docker build --tag=employee-manager:latest .

7.  docker run -p 8080:8080 employee-manager:latest

## Test API

1. First need to register a User /auth/register

2. You need to login registered user /auth/login

3. You need to put the returned token in login and add it as a header Authorization Bearer +TOKEN in any of the API endpoints.

## Access the application

-Base URL: http://localhost:8080

-Swagger UI: http://localhost:8080/swagger-ui-employee.html

## Database
-console:http://localhost:8080/h2-console

-db:jdbc:h2:mem:employeedb

-user:admin

-pass:admin

## API Endpoints

-POST /api/employees/batch - Create employeess

-GET /api/employees - Get all employees

-GET /api/employees{id} - Get employee by id

-GET /api/employees/search?name= - Get employees by Name

-PUT /api/employees/{id} - Update employee

-DELETE /api/employees/{id} - Delete employee

-ACTUATOR HEALTH  - /actuator/health

-REGISTER USER  - /auth/register

-LOGIN USER  - /auth/login


## Curl

- Create

  curl --location 'http://localhost:8080/api/employees/batch' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjMiLCJpYXQiOjE3NDk4Mzc1MzAsImV4cCI6MTc0OTkyMzkzMH0.Tun8uvyseKoA18IThe8fOqGxQCbncLab0uxyjRQTVUG4xvLQa_yflLPDiwIGtMDZpAoRoDUN3eKAfFsNaqdz3g' \
  --data '[
  {
  "primerNombre": "Juan Manuel",
  "apellidoPaterno": "Romero",
  "apellidoMaterno": "Fuentes",
  "edad": 40,
  "sexo": "F",
  "fechaNacimiento" : "13-11-1984",
  "puesto": "Developer",
  "estado": true
  },
  {
  "primerNombre": "Dario",
  "apellidoPaterno": "Romero",
  "apellidoMaterno": "FUentes",
  "edad": 40,
  "sexo": "M",
  "fechaNacimiento" : "13-11-1984",
  "puesto": "Director",
  "estado": true
  }
  ]'


- Update

  curl --location --request PUT 'http://localhost:8080/api/employees/1' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjMiLCJpYXQiOjE3NDk4Mzc1MzAsImV4cCI6MTc0OTkyMzkzMH0.Tun8uvyseKoA18IThe8fOqGxQCbncLab0uxyjRQTVUG4xvLQa_yflLPDiwIGtMDZpAoRoDUN3eKAfFsNaqdz3g' \
  --data '{
  "primerNombre": "juan Manuel",
  "edad": 40,
  "sexo": "M",
  "puesto": "Developer",
  "estado": false
  
  }'

- Delete

  curl --location --request DELETE 'http://localhost:8080/api/employees/1' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjMiLCJpYXQiOjE3NDk4Mzc1MzAsImV4cCI6MTc0OTkyMzkzMH0.Tun8uvyseKoA18IThe8fOqGxQCbncLab0uxyjRQTVUG4xvLQa_yflLPDiwIGtMDZpAoRoDUN3eKAfFsNaqdz3g'

- Get All Employees

  curl --location 'http://localhost:8080/api/employees' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjMiLCJpYXQiOjE3NDk4Mzc1MzAsImV4cCI6MTc0OTkyMzkzMH0.Tun8uvyseKoA18IThe8fOqGxQCbncLab0uxyjRQTVUG4xvLQa_yflLPDiwIGtMDZpAoRoDUN3eKAfFsNaqdz3g'

- Get Employee by id

  curl --location 'http://localhost:8080/api/employees/1' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjMiLCJpYXQiOjE3NDk4Mzc1MzAsImV4cCI6MTc0OTkyMzkzMH0.Tun8uvyseKoA18IThe8fOqGxQCbncLab0uxyjRQTVUG4xvLQa_yflLPDiwIGtMDZpAoRoDUN3eKAfFsNaqdz3g' \
  --data ''

- Get Employee by name

  curl --location 'http://localhost:8080/api/employees/search?name=Dario' \
  --header 'Content-Type: application/json' \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjMiLCJpYXQiOjE3NDk4Mzc1MzAsImV4cCI6MTc0OTkyMzkzMH0.Tun8uvyseKoA18IThe8fOqGxQCbncLab0uxyjRQTVUG4xvLQa_yflLPDiwIGtMDZpAoRoDUN3eKAfFsNaqdz3g' \
  --data ''

- Actuator Health

  curl --location 'http://localhost:8080/actuator/health'

- Register User

  curl --location 'http://localhost:8080/auth/register' \
  --header 'Content-Type: application/json' \
  --data '{
  "username": "admin3",
  "password": "cGFzc3dvcmQ="
  }'

- Login User

  curl --location 'http://localhost:8080/auth/login' \
  --header 'Content-Type: application/json' \
  --data '{
  "username": "admin3",
  "password": "cGFzc3dvcmQ="
  }'