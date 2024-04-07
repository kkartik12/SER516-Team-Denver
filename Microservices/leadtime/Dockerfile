FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean install

FROM openjdk:latest
WORKDIR /app
COPY --from=build /app/target/leadtime-0.0.1-SNAPSHOT.jar /app/leadtime.jar

CMD ["java", "-jar", "leadtime.jar"]