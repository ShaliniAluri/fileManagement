#define base docker image
FROM openjdk:17
LABEL maintainer="filemanagement.net"
ADD target/fileManagement-0.0.1-SNAPSHOT.jar  filemanagement.jar
ENTRYPOINT ["java","-jar","filemanagement.jar"]

#docker command to create docker image
#docker build -t filemanagement:latest .
#docker images - prints all docker images
#command to run image on container
#docker run -p 8081:8080 filemanagement


