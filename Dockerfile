# Build stage
FROM maven:3.9-eclipse-temurin-17-alpine AS backend-build

WORKDIR /app/backend

COPY backend/pom.xml .
RUN mvn dependency:go-offline -B

COPY backend/src ./src

RUN mvn package -DskipTests -B

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=backend-build /app/backend/target/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-Xms512m -Xmx1024m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
