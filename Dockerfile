FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY ./target/simple-todo-0.0.1-SNAPSHOT.jar todo-app.jar
EXPOSE 8080
CMD ["java", "-jar", "todo-app.jar"]