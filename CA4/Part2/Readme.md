## Class Assignment 4 - Part 2
The objective of this assignment was to utilize Docker to create two images and a docker-compose file, enabling the deployment of a containerized web application and an H2 database. Specifically, two Dockerfiles and a docker-compose file were created: one for running the web application and the other for hosting an H2 database. 

Similarly to the previous assignment copy the contents of the CA2 part 2 to the same folder as the Dockerfiles. This allows the Dockerfile to access the project files, enabling it to build and run the server.

### Dockerfile for the web application

The first Dockerfile, named Dockerfile.web, is designed to create an image that runs the web application. It copies the contents of the project directory, sets permissions on the gradlew file to make it executable, and then builds and runs the project. The Dockerfile is as follows:

```bash
FROM tomcat
RUN apt-get update && apt-get install -y dos2unix
WORKDIR /app

# Copy the project files to the container
COPY . .

# Expose the application port
EXPOSE 8080

# Set the gradlew file as executable and run the Spring Boot application
RUN dos2unix ./gradlew


ENTRYPOINT ["./gradlew"]
CMD ["bootRun"]
```

### Dockerfile for the H2 database

The second Dockerfile, Dockerfile.db, is responsible for creating an image that runs an H2 database. It installs the necessary dependencies, downloads the H2 database jar file, and starts the H2 database server. The file is as follows:

```bash
FROM ubuntu

RUN apt-get update && \
  apt-get install -y openjdk-17-jdk-headless && \
  apt-get install unzip -y && \
  apt-get install wget -y

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app/

# Download H2 Database and run it
RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar -O /opt/h2.jar

EXPOSE 8082
EXPOSE 9092

# Start H2 Server
CMD java -cp /opt/h2.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists
```

### Docker-compose file

The docker-compose.yml file orchestrates the creation and deployment of the web and database containers. It specifies the build context and Dockerfiles for each service, maps ports, and sets up a network for communication between the containers. The file is outlined below:

```bash
version: '3.8'

services:
db:
build:
context: .
dockerfile: Dockerfile.db
container_name: db
ports:
- "8082:8082"
- "9092:9092"
volumes:
- h2-data:/app/db-backup

web:
build:
context: .
dockerfile: Dockerfile.web
container_name: web
ports:
- "8080:8080"
depends_on:
- db
environment:
SPRING_DATASOURCE_URL: jdbc:h2:tcp://db:9092/./jpadb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE

volumes:
h2-data:
driver: local
```

### Running the application

1. To run the application, navigate to the directory containing the Dockerfiles and docker-compose.yml file and execute the following command:

```bash
docker compose up --build        
```
2. The web application will be accessible at http://localhost:8080, and the H2 database console can be accessed at http://localhost:8082.

3. To tag and push the image to Docker Hub, you can run the following commands:
```bash
docker tag <local_image>:<tag> <dockerhub_username>/<repository>:<tag>
```

```bash
docker push <dockerhub_username>/<repository>:<tag>
```

## Alternative Approach- Kubernetes 

An alternative solution for deploying the application and database involves using a Kubernetes cluster. This method enhances scalability and availability by deploying the application in one pod and the database in another. The application connects to the database via the service name, ensuring better separation of concerns and more efficient management.

Once the Docker images are pushed to Docker Hub, Kubernetes deployment files for both the application and the database need to be created. Below are examples of these deployment files:

# Web App Deployment File
This deployment file sets up the web application in a Kubernetes pod, specifying the Docker image to use and the port the application runs on. It ensures one replica of the pod is always running.

```bash
apiVersion: apps/v1
kind: Deployment
metadata:
   name: web-app-deployment
spec:
   replicas: 1
   selector:
      matchLabels:
         app: web
   template:
      metadata:
         labels:
            app: web
      spec:
         containers:
            - name: web
              image: yourusername/web:latest
              ports:
                 - containerPort: 8080
```

# Web App Service File
This service file exposes the web application to external traffic, mapping port 8080 of the pod to port 30080 on the host. It routes traffic to pods labeled app: web.

```bash
apiVersion: v1
kind: Service
metadata:
name: web-app-service
spec:
type: NodePort
ports:
- port: 8080
targetPort: 8080
nodePort: 30080
selector:
app: web
```

# Database Deployment File

This deployment file sets up the database in a Kubernetes pod, specifying the Docker image to use and the ports the database listens on. It ensures one replica of the pod is always running.

```bash
apiVersion: apps/v1
kind: Deployment
metadata:
   name: db-deployment
spec:
   replicas: 1
   selector:
      matchLabels:
         app: db
   template:
      metadata:
         labels:
            app: db
      spec:
         containers:
            - name: db
              image: yourusername/db:latest
              ports:
                 - containerPort: 8082
                 - containerPort: 9092
```

# Database Service File

This service file exposes the database to external traffic, mapping ports 8082 and 9092 of the pod to ports 30082 and 30092 on the host. It routes traffic to pods labeled app: db.

```bash
apiVersion: v1
kind: Service
metadata:
   name: db-service
spec:
   type: NodePort
   ports:
      - port: 8082
        targetPort: 8082
        nodePort: 30082
      - port: 9092
        targetPort: 9092
        nodePort: 30092
   selector:
      app: db
```

## Conclusion
Docker is simple and ideal for small projects or development, providing quick setup with Docker Compose but limited scalability and advanced features. Kubernetes, on the other hand, is robust and scalable, suitable for production environments with advanced features like auto-scaling, self-healing, and rolling updates. Use Docker for simplicity and development, and Kubernetes for complex, production-grade deployments.



