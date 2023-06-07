# Liceman project - DevForce 2024 practices
Liceman Project for DevForce2024 practices

## Requirements 
Java 17+ JDK

## Technologies
- Java 17
- SpringBoot 3 (3.0.5)
- Spring Security
- JWT Token for authentication/authorization
- H2 Database in a file (IntelliJ Idea Ultimate version compatibilty for DB integration
- Swagger to show and test ApiRest Collection

## First Steps
- Clone the repository
- Select master branch
- run command " mvn clean install " to install dependencies
- run the project. It should create 3 files for H2 in files, so you may consider set up your DB settings in IntelliJ Idea for integration. 
- You can use http://localhost:8080/swagger-ui/index.html#/ to see the ApiRest Collection (Then you can import that collection to Postman for example)

**IMPORTANT**: h2- in memory configuration may not work, thats why I prefer an embedded file bd this time.

## Tasks

- [x] Create the User class
- [x] Set up H2 DB 
- [x] Set up Sample Data 
- [x] Set up Spring Security
- [x] Test Basic Api
- [x] Set up a more complex security using roles and permissions for each role
- [x] Set up Swagger
- [x] Set up a demo for Api's access by using the roles given in sample data
- [x] Set up a CRUD service for User      
- [ ] Create Request class
- [ ] Create RequestService according to bussiness logic
- [ ] Create the ApiRest to test the BL for RequestService
