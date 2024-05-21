## Technical Report for Class Assignment 3 - Part 2 (Alternative)

### Introduction

This comprehensive guide walks you through the process of setting up a robust development environment using Vagrant with Hyper-V as the provider. The primary goal is to create a virtualized infrastructure capable of running a Spring Boot application tutorial, previously developed in CA2, Part 2. By virtualizing your development environment, you ensure consistency across systems and isolate dependencies from the host machine, facilitating collaboration and reproducibility.

### Initial Steps

1. Before running the Vagrant script, ensure that Vagrant is installed on your system and Hyper-V is enabled. 
2. Then, navigate to the directory containing the Vagrantfile in your terminal. 
3. Once there, execute the command "vagrant up" to create and provision the virtual machines as specified in the Vagrantfile. 
4. After the virtual machines are up and running, you can access the web server by opening a web browser and entering the IP address of the web server virtual machine followed by the port number 8080. 
5. Similarly to the previous step, you can access the H2 database by entering the IP address of the database virtual machine followed by the port number 8082 or 9092 in a web browser. These steps ensure that the virtual environment is set up correctly and ready for use.

### VM Configuration

- Set the base box to be used for the virtual machines, ensuring compatibility with Hyper-V.
```
Vagrant.configure("2") do |config|
config.vm.box = "hypervv/Ubuntu2004"
```

### Provider Configuration
- Sets the CPU count, memory size, maximum dynamic memory, and enables linked clones for the Hyper-V provider configuration.
```
config.vm.provider "hyperv" do |hv|
  hv.cpus = 2          
  hv.memory = 1024     
  hv.maxmemory = 2048 
  hv.linked_clone = true 
end
```
### Common Provisioning
- This part of the Vagrant configuration specifies a shell provisioner that executes shell commands within the virtual machine. It updates the package lists and installs necessary packages and dependencies using apt-get. The -y flag automatically answers "yes" to all prompts, ensuring an automated setup process.
```
config.vm.provision "shell", inline: <<-SHELL
sudo apt-get update -y
sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip openjdk-17-jdk-headless
SHELL
```

### Database VM Configuration
- This section of the Vagrantfile defines a virtual machine named "db" with specific configurations. It sets the hostname to "db" and assigns the IP address "192.168.56.11" on a private network. 
- Additionally, it provisions the VM with shell commands to download the H2 database and start the H2 server. The wget command downloads the H2 database JAR file, and the subsequent java command launches the H2 server with specified options, including enabling web and TCP connections and logging output to a file named "h2-server.log".

```
config.vm.define "db" do |db|
db.vm.hostname = "db"
db.vm.network "private_network", ip: "192.168.56.11"

    # Specific provisioning for the database VM
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
      java -cp h2-1.4.200.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/h2-server.log 2>&1 &
    SHELL
end
```
### Web VM Configuration and Provisioning
- Firstly, this part of the Vagrantfile defines a virtual machine named "web" with specific configurations. It sets the hostname to "web" and assigns the IP address "192.168.56.10" on a private network. 
- The VM is provisioned with shell commands to install Tomcat 9, clone a repository from GitHub, configure permissions for the repository directory, build a Gradle project, and run a Spring Boot application.

```
config.vm.define "web" do |web|
web.vm.hostname = "web"
web.vm.network "private_network", ip: "192.168.56.10"

    web.vm.provision "shell", inline: <<-SHELL
      sudo apt install -y tomcat9 tomcat9-admin
   
      git clone https://github.com/tiagodarochapereira/devops-23-24-JPE-1231861.git
      sudo chown -R vagrant:vagrant /home/vagrant/devops-23-24-JPE-1231861/

      cd devops-23-24-JPE-1231861/CA2/part2
      chmod +x ./gradlew
      ./gradlew build
      nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
    SHELL
end
end
```

### Conclusion

In conclusion, this Vagrant implementation provides a comprehensive setup for creating a virtual environment using Hyper-V as the provider. By following the instructions outlined in the README, users can efficiently deploy and manage virtual machines to execute their projects.

One of the main advantages of using Hyper-V with Vagrant is its compatibility with Windows operating systems, making it suitable for developers working in Windows environments. Hyper-V also offers features like dynamic memory allocation and linked clones, which can help optimize resource usage and speed up the creation of virtual machines.

However, compared to other Vagrant implementations, such as those using VirtualBox or VMware, Hyper-V may have some limitations. For example, Hyper-V is only available on Windows 10 Pro, Enterprise, or Education editions, limiting its use for developers on other platforms. Additionally, some users may find the configuration and setup process for Hyper-V to be more complex compared to other providers.

Overall, the choice of Vagrant provider depends on factors such as the developer's operating system, project requirements, and familiarity with the provider's features. 

Repository URL: https://github.com/tiagopereiraswitch/devops-23-24-JPE-1231861





