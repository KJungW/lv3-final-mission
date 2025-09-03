FROM gradle:8.6-jdk21 AS build
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle ./gradle
COPY src ./src
RUN ./gradlew bootJar -x test --no-daemon

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS:-} -Dspring.profiles.active=${SPRING_ACTIVE_PROFILE:-dev} -jar /app/app.jar"]
