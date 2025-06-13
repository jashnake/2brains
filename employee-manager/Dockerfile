FROM openjdk:17-jdk-alpine
COPY "/target/employee-manager-0.0.1-SNAPSHOT.jar" "/app/employee-manager.jar"
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","employee-manager.jar"]