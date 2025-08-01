# Start your image with a node base image
FROM openjdk:17-jdk-slim

COPY boardproject.jar app.jar

EXPOSE 8080

# Start the app using serve command
CMD [ "java", "-jar", "/app.jar" ]