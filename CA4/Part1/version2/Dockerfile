FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . /app

EXPOSE 59001

CMD ["java", "-cp", "basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]