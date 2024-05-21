### Technical Report for Class Assignment 3 - Part 2

## Introduction

Vagrant is a tool used to optimize the creation and management of virtual development environments. It offers developers
a command-line interface for automating tasks related to setting up and configuring virtual machines. By defining the
desired environment in configuration files called Vagrantfiles, developers can easily replicate consistent development
setups across various platforms. This simplifies the process of creating virtualized environments, promoting
collaboration among team members and ensuring uniformity in development environments across projects.
In this segment of Class Assignment 3 (CA3), we will explore the process of setting up and configuring virtual machines
using Vagrant.

### Initial Steps

1. Install Vagrant on your computer by downloading the installer from the official
   website [here](https://www.vagrantup.com/downloads).

2. Check if Vagrant is installed by running the following command:

```bash
vagrant --version
```

3. Afterwards you need to create a Vagrantfile, in this case, it'll be created in the CA3/part 1 folder, you can start
   by just creating a new basic file called Vagrantfile in the CA3/part2 folder:

```bash
cd DevOpsRep/CA3/part2
touch Vagrantfile 
```

4. Afterwards, a script must be added to the Vagrantfile to configure the virtual machine. The following script can be
   used:

```bash
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/focal64"
  config.ssh.insert_key = false
  ```

### Vagrantfile Configuration
- This section provisions common packages for both VMs. It updates the package lists and installs essential packages such
as iputils-ping, avahi-daemon, libnss-mdns, unzip, and openjdk-17-jdk-headless. The commented out ifconfig command can
be used to verify the network configuration.

```bash       
  config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
        openjdk-17-jdk-headless
    # ifconfig
  SHELL
```
### Database VM Configuration: Define VM

- Afterwards, we define the configuration specific to the database VM. We set its box to "ubuntu/focal64" again, assign
the hostname "db", and configure a private network with a static IP address of "192.168.56.11".
#We also forward ports 8082 and 9092 from the guest VM to the host machine to access the H2 console and server,
respectively. We then download the H2 database using the wget command and provision a shell script to run the H2 server
process.

```bash
  config.vm.define "db" do |db|
    db.vm.box = "ubuntu/focal64"
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"
```

### Database VM Configuration: Forward Ports

- The following lines forward ports 8082 and 9092 from the guest VM (database VM) to the host machine. These ports are
used to access the H2 console and server, respectively.

```bash
    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092
```
### Database VM Configuration: Download H2 Database

- This section downloads the H2 database JAR file using wget, which is necessary for running the H2 server.

```bash
    # We need to download H2
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL
```

### Database VM Configuration: Start H2 Server

- Finally, this provisioner runs the H2 server process always, allowing external connections. It starts the H2 server
with web and TCP access, and redirects output to a log file.

```bash
db.vm.provision "shell", :run => 'always', inline: <<-SHELL
java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
SHELL
```

## Webserver VM Configuration: Define VM

* Begins defining a virtual machine (VM) named "web".
* Sets the box to "ubuntu/focal64", assigns the hostname "web", and configures a private network with a static IP
  address of "

```bash
  config.vm.define "web" do |web|
    web.vm.box = "ubuntu/focal64"
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"
```

### Webserver VM Configuration: Forward Ports

* The following section configures the web virtual machine by installing Tomcat 9 and cloning a Git repository
  containing a project.
* Afterwards it builds the project using Gradle and starts the Spring Boot application as a background process, logging
  its output for monitorin

```bash
    web.vm.provision "shell", inline: <<-SHELL, privileged: false
       sudo apt install -y tomcat9 tomcat9-admin
       git clone https://github.com/tiagopereiraswitch/devops-23-24-JPE-1231861.git
            cd devops-23-24-JPE-1231861/CA2/part2
      chmod u+x gradlew
      ./gradlew clean build
      nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
    SHELL
```

### Starting the Virtual Machines

1. After creating the Vagrantfile, you can start the virtual machines by running the following command:

```bash
vagrant up
```

- Note: Opening the servers isn't needed, simply open the VirtualBox application before running the command.

* In case you want to stop the VMs, you can run the following command:

```bash
vagrant halt
```

* If you want to destroy the VMs, you can run the following command:

```bash
vagrant destroy
```

This will remove the VMs and all the data inside them.

## Other Vagrant commands that can be useful are:

```bash
vagrant reload 
```

This command restarts the VMs, applying any changes made to the Vagrantfile.

```bash
vagrant suspend
```

This command suspends the VMs, saving their current state.

```bash
vagrant status
```

This command displays the status of the VMs.

```bash
vagrant provision
```

This command starts the VMs and applies any changes made to the Vagrantfile.

```bash
vagrant global-status
```

This command displays the status of all Vagrant environments on the system.

```bash
vagrant init
```

This command initializes a new Vagrant environment by creating a new Vagrantfile.

### Conclusion

The Vagrantfile serves as the blueprint for creating and managing a development environment composed of multiple virtual
machines and orchestrates the setup of each VM
In this class assignment, the Vagrantfile was utilized to configure a development environment consisting of multiple
virtual machines. 

Some of the tasks included provisioning software installations like Tomcat and Git, cloning a project
repository, and building and running a Spring Boot application. Each virtual machine was configured with its own
hostname, IP address, and provisioning scripts, ensuring a cohesive and reproducible development environment for
collaborative projects.

Repository URL: https://github.com/tiagopereiraswitch/devops-23-24-JPE-1231861







