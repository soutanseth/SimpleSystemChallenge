# SimpleSystemChallenge - Todo Service
***
This is a Simple Todo Service which allows to manage Todo items

## The service provides RESTful API(s) that allows to:
1. Add a Todo Item,
2. Change description of an item,
3. Mark an item as "done" or "not done"
4. Get all items that are "not done"
5. Get all items
6. Get details of a specific item.

## Also the Todo Service has the following functionalities:
1. The service automatically change status of items that are past their due date as "past due".
2. The service should forbid changing "past due" items via its REST API.

## Assumptions
1. The user (or the client) of the service is in the same time zone
2. Due date time of a Todo Item is a future date
3. Date format used is: _yyyy-MM-dd HH:mm_
4. No Authentication is used

## Tech Stack used
1. Java 1.8
2. Spring Boot Web MVC with RESTful service
3. Spring Boot JPA
4. H2 Database
5. Spring Boot Validation
6. Springdoc Openapi for Api Documentation
7. Maven
7. Docker

## Pre-requisites
1. Java 1.8
2. Maven 3.9.2
3. Docker(optional)
4. Postman(for testing/using the Api(s))

## How to
### Build the service
1. clone the Git repository: [SimpleSystemChallenge Repo](https://github.com/soutanseth/SimpleSystemChallenge.git)
2. Goto the project directory
3. run command: **mvn clean install**
4. If Maven is not installed then use ./mvnw clean install (for Unix) or mvnw.cmd clean install (for Windows) [_Not tested locally_]

### Running automatic tests
1. Goto the project directory
2. run command: **mvn test**

### Running the service
1. Once the project is built, go to target directory
2. run command: **java -jar simple-todo-0.0.1-SNAPSHOT.jar**

### Building and running as Docker container
1. From the project directory run : **docker build -t todo-service-image .**
2. Then run: **docker run --name todo-service --rm -d -p 8080:8080 todo-service-image**

### Api Details
1. Once the service is up and running, open browser and navigate to  **http://localhost:8080/swagger-ui/index.html**  to see the list of Api(s)

### Using/Testing the Apis
1. Open and use Postman to test or make use of the Api(s)
2. Postman collection with sample requests can be found [here](/simple-todo/SimpleSystem_Challenge.postman_collection.json)