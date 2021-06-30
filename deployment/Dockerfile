# Select parent image
FROM maven:3.8.1-jdk-11

COPY ../ ./

# Package the application code
RUN mvn clean package

# Set the startup command
CMD ["java", "-jar", "target/ecommerce-0.0.1-SNAPSHOT.jar"]
