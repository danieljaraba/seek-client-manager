FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

RUN apt-get update && apt-get install -y unzip && rm -rf /var/lib/apt/lists/*

COPY . .

RUN chmod +x gradlew

RUN ./gradlew clean build --no-daemon

FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
