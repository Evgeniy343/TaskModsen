FROM maven:3.8.1-openjdk-11 AS build-function
RUN mkdir app
COPY . app/
WORKDIR app/
RUN mvn install
RUN mvn clean package -DskipTests

FROM adoptopenjdk/openjdk11
EXPOSE 8080
COPY --from=build-function app/target/*.jar modsen.jar
ENTRYPOINT ["java","-jar", "modsen.jar"]