# Start your image with a node base image
FROM openjdk:17-jdk-slim

COPY BoardProject-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Start the app using serve command
CMD [ "java", "-jar", "/app.jar" ]