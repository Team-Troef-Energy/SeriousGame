FROM maven:3-eclipse-temurin-17-alpine AS build
WORKDIR /src
COPY . .
RUN mvn install

FROM eclipse-temurin:17-jre-alpine AS final
WORKDIR /app
COPY --from=build /src/target/app.jar .
USER 1000
CMD ["java", "-jar", "app.jar"]
