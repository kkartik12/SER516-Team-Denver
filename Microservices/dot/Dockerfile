FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean install

FROM openjdk:latest
WORKDIR /app
COPY --from=build /app/target/dot-0.0.1-SNAPSHOT.jar /app/dot.jar

CMD ["java", "-jar", "dot.jar"]