# Giai đoạn 1: Build file .jar
# (Đây là cách nâng cao "multi-stage", nó build ngay trên docker)
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Bỏ qua test
RUN mvn clean package -DskipTests

# Giai đoạn 2: Tạo image chạy ứng dụng
# Sử dụng base image Java 21
FROM openjdk:21-slim-jre
WORKDIR /app

# Lấy file .jar từ giai đoạn "build"
COPY --from=build /app/target/*.jar app.jar

# Cổng mà Spring Boot đang chạy
EXPOSE 8080

# Lệnh để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]