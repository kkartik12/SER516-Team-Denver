FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean install

FROM openjdk:latest
WORKDIR /app
COPY --from=build /app/target/found_work-0.0.1-SNAPSHOT.jar /app/found_work.jar

CMD ["java", "-jar", "found_work.jar"]