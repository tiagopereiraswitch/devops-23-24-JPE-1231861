## Class Assignment 5

### Introduction

The goal of this class assignment was to create a Jenkins pipeline that builds and pushes images to Docker. Initially, a simpler pipeline was created for practice, which included similar tasks such as Checkout, Assemble, Test, and Build. The second, more complex pipeline, included an additional step to push the Docker image to DockerHub. The final result of the assignment can be found here.

### Initial Steps

1. To start, you need to download the latest Docker version from the official website. In this case the Jenkins 2.452.2 LTS version was used.
2. Secondly, open the directory where the Jenkins.war file is located and run the command `java -jar jenkins.war --httpPort=[port number].
3. Open a browser and go to the address `localhost:[port number]` to access Jenkins.
4. To install the necessary plugins, go to the Jenkins dashboard, click on Manage Jenkins, then Manage Plugins, and install the following plugins:
    
    - HTML Publisher
    - Docker Pipeline
    - Docker Commons Plugin
    - Docker Plugin
    - Docker API Plugin

5. After installing the plugins, go to your dashboard, click on New Item, and create a new pipeline.
6. Afterwards go to the Configuration page and add the following code to the Pipeline section. This code will be used to create the pipeline for the first part of the assignment and it 
will be responsible for defining the stages of the pipeline, such as Checkout, Assemble, Test, and Archive and the tasks that will be executed in each stage:

### Jenkinsfile for Part 1:
```
pipeline {
agent any

    environment {
        REPO_URL = 'https://github.com/tiagopereiraswitch/devops-23-24-JPE-1231861.git'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out the code...'
                git url: "${REPO_URL}", branch: 'master'
            }
        }

        stage('Assemble') {
            steps {
                dir('CA2/Part1') {
                    echo 'Assembling the application...'
                    bat 'gradlew.bat assemble'
                }
            }
        }

        stage('Test') {
            steps {
                dir('CA2/Part1') {
                    echo 'Running unit tests...'
                    bat 'gradlew.bat test'
                    junit 'build/test-results/test/*.xml'
                }
            }
        }

        stage('Archive') {
            steps {
                dir('CA2/Part1') {
                    echo 'Archiving artifacts...'
                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                }
            }
        }
    }
}
```
The  Jenkins pipeline that is shown above automates the build, test, and archiving process for a Java application using Gradle. It consists of four stages: Checkout retrieves the code from the specified Git repository, Assemble compiles the application, Test runs unit tests and publishes the results, and Archive archives the compiled .jar files.
Jenkinsfile for Part 2:
```
pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = '37bc4c40-8962-4197-a165-f6a353dc64e4'
        DOCKER_IMAGE = 'tiagopereiraswitch/springbootapp'
        DOCKER_TAG = "${env.BUILD_ID}"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out the code...'
                git url: 'https://github.com/tiagopereiraswitch/devops-23-24-JPE-1231861.git', branch:'main'
            }
        }
        
        stage('Set Permissions') {
            steps {
                dir('CA2/part2/') {
                    echo 'Setting executable permissions on gradlew...'
                    bat 'gradlew.bat'
                }
            }
        }
        
        stage('Assemble') {
            steps {
                dir('CA2/part2/') {
                    echo 'Assembling the application...'
                    bat './gradlew.bat assemble'
                }
            }
        }
        
        stage('Test') {
            steps {
                dir('CA2/part2/') {
                    echo 'Running unit tests...'
                    bat './gradlew.bat test'
                }
            }
        }
        
        stage('Javadoc') {
            steps {
                dir('CA2/part2/') {
                    echo 'Generating Javadoc...'
                    bat './gradlew.bat javadoc'
                    publishHTML(target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: false,
                        keepAll: true,
                        reportDir: 'build/docs/javadoc',
                        reportFiles: 'index.html',
                        reportName: 'Javadoc'
                    ])
                }
            }
        }
        
        stage('Archive') {
            steps {
                dir('CA2/part2/') {
                    echo 'Archiving artifacts...'
                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                }
            }
        }
        
       stage('Create Dockerfile') {
            steps {
                dir('CA2/part2/') {
                    script {
                        def dockerfileContent = """
                        FROM tomcat:10
                        RUN apt-get update && apt-get install -y dos2unix
                        WORKDIR /app
                        COPY . .
                        EXPOSE 8080
                        RUN dos2unix ./gradlew
                        ENTRYPOINT ["./gradlew"]
                        CMD ["bootRun"]
                        """
                        writeFile file: 'Dockerfile', text: dockerfileContent
                    }
                }
            }
        }
        
        stage('Publish Image') {
            steps {
                script {
                    echo 'Building and publishing Docker image...'
                    docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS_ID}") {
                        dir('CA2/part2/') {
                            def customImage = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                            customImage.push('latest')
                        }
                    }
                }
            }
        }
    }
}

```

The main difference from the previous pipeline is the inclusion of stages for setting executable permissions on the Gradle wrapper, generating and publishing Javadoc, creating a Dockerfile, and building and publishing a Docker image.

In this Jenkinsfile, the Docker image is pushed to DockerHub. To achieve this, we need to add DockerHub credentials in Jenkins. During the pipeline setup, there's an option to add credentials where we enter our DockerHub username and password. These credentials are then named (in this case, they are called DOCKERHUB_CREDENTIALS).
You need to generate a new token in DockerHub and use its ID as the CREDENTIALS_ID. When creating the token make sure you use your DockerHub's account username and password. This can be checked in the Credentials section of Jenkins by clicking on the Jenkins dashboard, then Manage Jenkins, and finally Manage Credentials.

## Conclusion

n conclusion, this report outlines the setup of Jenkins pipelines for a project. The pipelines were designed to automate key stages of the development process, including code checkout, assembly, testing, artifact archiving, and Docker image publishing.

The first pipeline served as a simpler practice version, covering the essential tasks of checkout, assembly, testing, and archiving. In contrast, the second, more sophisticated pipeline included additional stages and pushing a Docker image to DockerHub.

The report centers around the importance of using the correct commands and environment variables tailored to the operating system running Jenkins. It also demonstrated troubleshooting techniques and provided solutions to common errors, ensuring the successful execution of the Jenkins pipelines.

Setting up these Jenkins pipelines marks a significant advancement in automating and streamlining the development process. This not only improves productivity but also enhances efficiency.

Repository URL: https://github.com/tiagopereiraswitch/devops-23-24-JPE-1231861