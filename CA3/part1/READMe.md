# Technical Report for Class Assignment 3 - Part 1

## Introduction

Virtualization provides a robust solution, enabling developers to simulate diverse operating systems on a single hardware platform. This segment of Class Assignment 3 (CA3) focuses on utilizing VirtualBox and Vagrant.

The primary aim of this assignment is to perform virtualization techniques to migrate and execute existing projects from previous assignments within a virtualized Ubuntu environment. This setup ensures uniformity across different systems, effectively managing all functionalities and dependencies in isolation from the host systems.

This report encompasses the processes involved in setting up essential development tools, and executing the two projects done in the previous class assignments: a Spring Boot and a Gradle demonstrations.

It is important to note that before following the steps outlined in this report, the reader should have the Ubuntu 20.04.3 LTS installed on the Virtual Box. The download link is available [here](https://releases.ubuntu.com/20.04/).


- Head to [Oracle's website](https://www.virtualbox.org/) and download VirtualBox.

### Virtual Machine Setup

1. In VirtualBox, create a new VM and choose the following specifications:

* Type: Linux
* Version: Ubuntu (64-bit)
* It is recommended to allocate 2048 MB RAM(take your computer's specifications into account)
* Allocate at least 20 GB of space for the virtual hard disk. Choose "VDI (VirtualBox Disk Image)" as the hard disk file type, and select "Dynamically allocated" for storage on the physical hard disk.
* Select the Ubuntu Server ISO file from your local storage and click "Open" to mount it to the virtual machine.
* Start the VM and follow the on-screen instructions to install Ubuntu. Choose the standard utilities for a server and install the OpenSSH server if prompted to enable remote connections.


2. 
* Open VirtualBox and select your virtual machine from the list.
* Click on "Settings" to open the settings for the virtual machine.
* Navigate to the "Network" tab.
* For Network Adapter 1, select "NAT" from the "Attached to" drop-down menu. This will allow your virtual machine to access the internet through your host's network connection.
* For Network Adapter 2, select "Host-only Adapter" from the "Attached to" drop-down menu. Choose "vboxnet0" from the "Name" drop-down menu. This will create a private network between your host and the virtual machine.
* Click "OK" to save the changes and close the settings window.

3.

* Determine the IP address range of the Host-only network (e.g., 192.168.56.1/24).

* Assign an appropriate IP from this range to the adapter in the VM. This can be done by editing the network configuration file within the VM.

* Update the network configuration within the VM:
* 
 ```bash
  $ sudo apt update
  ```

* Access the network configuration file in your VM. This is typically located in /etc/netplan/.

* Edit the configuration file using a text editor such as nano

* Modify the network settings to specify the IP address, subnet mask, gateway (if applicable), and DNS servers. For example:

* Finally , configure the network interface by editing the configuration file. In this case, the file is named 01-netcfg.yaml:
  
```bash
  $ sudo nano /etc/netplan/01-netcfg.yaml
  ```
* Add the following configuration to the file, replacing the IP address, subnet mask, gateway, and DNS servers with the appropriate values:
  ```yaml
  network:
    version: 2
    renderer: networkd
    ethernets:
      enp0s3:
        dhcp4: yes
      enp0s8:
        addresses:
          192.168.56.7/24 (example)
  ```

* Apply the changes by running the following command:
  ```bash
  $ sudo netplan apply
  ```
* Verify the network configuration by running the following command:
  ```bash
    $ ip a
    ```
The output should display the configured IP address for the Host-only network adapter.

### Initial Steps
1. Install git on your VM using the following command:
```bash
sudo apt update
sudo apt install git
```

2. Afterwards, Maven should be installed since it's needed to perform the tasks in the Class Assignment 1. The following command should be used:
```bash
sudo apt install maven
```
3. Install java 17, this java version is used to allow the running of the tasks of the previous project from the Class Assignment 2. The following command should be used:
```bash
sudo apt install openjdk-17-jre
```
4. Lastly, install gradle on your virtual machine in order to run the tasks of the Class Assignment 2. The following command should be used:
```bash
sudo apt install gradle
```

### Repository Setup

1. In case your repository is private, before cloning it into your VM, you should generate an SSH key and add it to your GitHub account. The following command should be used to generate the SSH key:
```bash
ssh-keygen -t rsa 
```
The ssh-keygen command is used to generate SSH keys, meanwhile the "-t rsa" is used to specify the type of key to create, in this case, RSA.

2. After generating the SSH key you should copy the key and add it to your GitHub account. Go to your GitHub account, click on your profile picture, then click on settings, then click on SSH and GPG keys, and finally click on New SSH key. Paste the key and click on Add SSH key.

3. Clone the repository into your VM using the git clone command, in my case it was as follows:
```bash
git clone git@github.com:tiagopereiraswitch/devops-23-24-JPE-1231861.git
```
### Running the Class Assignment 1 Project

1. After cloning the repository, navigate to the CA1 folder, in my case it was as follows:
```bash
cd Projects\Switch\DevOps\Pratica\DevOpsRep\CA1\basic
```

2. Execute the following command to remove any previous build and compile the project:
```bash
./mvnw clean install
```

3. Finally to run the project, execute the following command:
```bash
./mvnw spring-boot:run
```
4. To access and visualize the project front-end, open a browser and type the following address:
```bash
http://(your localhost):8080/
```

### Running the Class Assignment 2 Part 1 Project

1. After cloning the repository, navigate to the CA2 folder, in my case it was as follows:
```bash
cd Projects\Switch\DevOps\Pratica\DevOpsRep\CA2\part1
```
2. In this case it might be needed to use the following command to allow the execution of the gradlew file in your VM:
```bash
chmod +x gradlew
```

3. Compile the project by executing the following command, similar to what was done in the class assignment:
```bash
./gradlew build
```

4. To run the server, execute the following command:
```bash
./gradlew runServer
```

5. Afterwards, run the client in the same folder but on the computer terminal:
```bash
./gradlew runClient --args="192.168.56.5 59001" 
```
The arguments are the IP of the VM and the port of the server.

### Running the Class Assignment 2 Part 2 Project

1. Navigate to the CA2/part 2 folder in the repository, in my case it was as follows:
```bash
cd Projects\Switch\DevOps\Pratica\DevOpsRep\CA2\part2
```

2. Once again compile the project by executing the following command:
```bash
./gradlew build
```

3. Finally run the project by executing the following command:
```bash
./gradlew bootRun
```
4. Similarly to what was previously done, type the VM IP address and the port number on your browser to access the project front-end:
```bash
192.168.56.5:59001
```
This concludes the technical report for the Class Assignment 3 - Part 1.

Repository URL: https://github.com/tiagopereiraswitch/devops-23-24-JPE-1231861