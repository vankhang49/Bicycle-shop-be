# Build stage
FROM gradle:7.6.1-jdk21 AS build
WORKDIR /app

COPY . .
RUN gradle clean build -x test


# Run stage

FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=build /app/build/libs/DrComputer-0.0.1-SNAPSHOT.jar drcomputer.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","drcomputer.war"]