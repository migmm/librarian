FROM openjdk:17-jre-slim

WORKDIR /app

COPY target/librarian-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]
