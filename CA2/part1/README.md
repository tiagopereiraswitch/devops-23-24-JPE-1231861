# Class Assignment 2

## Introduction

The aim of this assignment was to work with a basic Gradle application to incorporate new functionalities and to
develop a deeper understanding of navigating the Gradle wrapper and making adjustments to the build.gradle setup.

### Initial steps

The first step consists in cloning the following repository: https://bitbucket.org/pssmatos/gradle_basic_demo/ to the
local machine, it will serve as the foundation in completing the tasks.

```bash
git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git
```

The rest of the assignment can be done by opening a bash terminal and running the following commands:

1. Navigate to the project directory:
   ```bash
   cd path/to/gradle_basic_demo
   ```


2. Copy the application into the CA2/Part1 folder:
   ```bash
   cp -r . ../DevOpsRep/CA2/part1
   cd ../DevOpsRep/CA2/part1
   ```
3. Add all files to the staging area:
   ```bash
   git add .
   ```


4. Commit the files that were previously added:
   ```bash
   git commit -m "[Feat] Close #9 Cloned repository provided for the CA2 part1"
   ```

5. Push the commit to the remote repository:
   ```bash
   git push
   ```

### Task Completion- Implementation of Changes

#### Part 1: Addition of the runServer task

For this first section, the goal is to add a new task in the build.gradle file to start the server.

1. Open the build.gradle file that is present in the project and add the runServer task:

   ```gradle
   task runServer(type:JavaExec, dependsOn: classes){
    group = "DevOps"
    description = "Launches a chat server that listens to a client on localhost:59001"

    classpath = sourceSets.main.runtimeClasspath

    mainClass = 'basic_demo.ChatServerApp'

    args '59001'
   }

    ```

2. Run the runServer task in the terminal:

```bash
./gradlew runServer
```

3. Add and commit the changes:

```bash
git commit -a -m "Add a new task to update the server"
```

This Gradle script defines a task named "runServer" of type JavaExec, which means it will execute a Java class. This
task is configured to depend on the "classes" task, implying that it will run only after the compilation of the Java
classes is completed.
The argument "59001", indicating the port number on which the chat server will listen for client connections.

#### Part 2: Adding the Test Class and respective unit test

For the aforementioned task, a new test class and its unit test will be added:

1. Create a new test folder and a new test class:
   ```bash
   mkdir -p src/test/java/basic_demo
   touch src/test/java/basic_demo/AppTest.java
   ```


2. Add the unit test:

```java

@Test
public void testAppHasAGreeting() {
    App classUnderTest = new App();
    assertNotNull("app should have a greeting", classUnderTest.getGreeting());
}
```

3. Add the necessary junit dependencies in the build.gradle file:

```gradle
dependencies {
    // Use Apache Log4J for logging
    implementation group: 'org.apache.logging.log4j', name: 'log4j-api', version: '2.11.2'
    implementation group: 'org.apache.logging.log4j', name: 'log4j-core', version: '2.11.2'
    testImplementation 'junit:junit:4.12'
}
```

4. Compile the project:

```bash
./gradlew build
```

5. Add and commit the changes:

```bash
git commit -a -m "Add a simple unit test and update the gradle script so that it executes the test"
```

6. Push the previous changes to the remote repository:

```bash
git push
```

#### Part 3: Adding the Copy task

This part consists in creating a task to create a backup of the source of the application and copy it into a 'backup'
folder:

1. Open the build.gradle and add the backupSource task:

```gradle
task backupSource(type:Copy){
    group = "DevOps"
    description = "The source code is copied into the backup directory"

    from '/src'
    into '/backup'
}
```

5. Compile the project in the terminal:

```bash
./gradlew build
```

Run the backupSource task in the terminal:

```bash
./gradlew backupSource
```

6. Add and commit the changes:

```bash
git commit -a -m "Add a new task of type Copy to be used to make a backup of sources of the application."
```

7. Push the changes to the remote repository:

```bash
git push
```

#### Part 3: Adding the Zip task

The final part of the part 1 of this assignment consists in creating a task to zip the source files of the application
and copy it into the distributions folder:

1. Open the build.gradle file and add the zipSource task:

```gradle
task zipSource(type:Zip){
    group = "DevOps"
    description = "The source code gets zipped into a zip file"

    from '/src'
    archiveBaseName.set("source")
    archiveExtension.set("zip")
}

```

5. Compile the project in the terminal:

```bash
./gradlew build
```

Run the zipSource task in the terminal:

```bash
./gradlew zipSource
```

6. Add and commit the changes:

```bash
git commit -a -m "Add a new task of type Zip to be used to make an archive of the sources of the application"
```

7. Push the changes to the remote repository:

```bash
git push
```

8. Add a tag to mark the end of the first part of this assignment:

```bash
git tag ca2-part1
git push --tags
```

Repository URL: https://github.com/tiagopereiraswitch/devops-23-24-JPE-1231861