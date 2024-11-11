# Step 1: Use a Maven image with OpenJDK 17 to build the application
FROM maven:3.8.7-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy the source code to the container
COPY . .

# Package the application as a JAR file
RUN mvn clean package -DskipTests

# Step 2: Use a lightweight JDK image to run the application
FROM eclipse-temurin:17-jdk-jammy

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/OrderManagement-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
