# Build stage
FROM gradle:7.6.1-jdk17 AS build
WORKDIR /app

# Install JDK 21
RUN apt-get update && \
    apt-get install -y curl unzip zip && \
    curl -s "https://get.sdkman.io" | bash && \
    bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install java 21.0.0-tem" && \
    bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk use java 21.0.0-tem && java -version"

COPY . .
RUN gradle clean build -x test


# Run stage

FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=build /app/build/libs/DrComputer-0.0.1-SNAPSHOT.jar drcomputer.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","drcomputer.war"]
