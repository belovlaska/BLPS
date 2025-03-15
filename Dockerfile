FROM gradle:8.10.2-jdk17-alpine as builder
WORKDIR /app
COPY . .
RUN ./gradlew clean build bootJar

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
RUN export $(cat .env | xargs)
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
