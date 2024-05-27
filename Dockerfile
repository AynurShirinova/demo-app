FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/my-java-app.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]
EXPOSE 8083
