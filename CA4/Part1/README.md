# Class Assignment 4 Part 1

## Introduction

The objective of this class assignment was to utilize Docker to create an image and run a container using that image. 
For this task, two Dockerfiles were developed, each featuring a different version of the same application.


## Version 1

 For this version of the assignment, the sole requirement is to create a Dockerfile. 
Clone a repository containing the already implemented project in CA2 part 1, build it and afterwards run the application. Before running the app, you might need to change the permissions of the gradlew in order to execute it.
Lastly, build and run the application and the chat server will be launched on port 59001. The Dockerfile for version 1 is the following:

```dockerfile

# This line is used to set the base image for the application. In this case it is set to openjdk:17-jdk-slim
FROM openjdk:17-jdk-slim

#This line indicates the working directory for the application. In this case it is set to /app, the folder where the application will be copied to.
#The folder will be created if it does not exist.
WORKDIR /app
#This line is used to copy the contents of the current directory to the /app folder in the container.
COPY . /app

#This line is used to run the gradlew script with the build command. This will build the application inside the container.
RUN chmod +x gradlew 

#This line is used to expose the port 8080 to the host machine. This is the port where the chat server will be running.
EXPOSE 8080
#Lastly, this line is used to run the chat server with the specified port.
CMD ["./gradlew", "build", "runServer"]
```
### Running the Server
1. To create the image, navigate to the folder where the Dockerfile is located and run the following command:
```bash
docker build -t chat-server.
```
* The -t flag is used to tag the image with a name and optionally a tag in the 'name:tag' format. In this case the image is tagged as chat-server.
Do not forget the dot at the end of the command, it is used to specify the location of the Dockerfile.

2. After the image is created, run the following command to start the container:
```bash
docker run -p 59001:59001 chat-server
```
3. Afterwards, the chat client can be started by running the following command:
```bash
./gradlew runclient
```

4. To push the image to Docker Hub, you can run the following commands:
```bash
docker tag chat-server (your_dockerhub_username)/chat-server 
```
```bash
docker push (your_dockerhub_username)/chat-server
```



## Version 2

In this version, we copy the project files directly from the local machine, build them, and run the server. Follow these steps to prepare the Dockerfile and run the application:


1. Similarly to what it was done in the version1, navigate to the CA4/Part1/version2 directory and run:

```bash
cp -r ../../../CA2/Part1/gradle_basic_demo .
```
Create the Dockerfile:
The Dockerfile for Version 2 is as follows:

Dockerfile
```dockerfile
# Set the base image to openjdk:21-jdk-slim
FROM openjdk:17-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the contents of the current directory to /app in the container
COPY . /app

# Expose port 59001 for the chat server
EXPOSE 59001

# Run the chat server with the specified port
CMD ["java", "-cp", "basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]
```

### Running the Server

1. To build the image, navigate to the directory where the Dockerfile is located and run the following command:
```bash
docker build -t chat-server .
```
2. After the image is created, run the following command to start the container:
    
```bash
docker run -p 59001:59001 chat-server
```

## Conclusion

The main difference between the two Dockerfile versions is in the build process. Version 1 builds the project inside the Docker container using Gradle, which involves copying the source code, installing build dependencies, and executing the build commands. In contrast, Version 2 assumes the project is already built and only copies the pre-built JAR file into the container, running it directly without any additional build steps. This makes Version 2 faster and simpler, as it avoids the build process inside the container.

Repository URL: https://github.com/tiagopereiraswitch/devops-23-24-JPE-1231861