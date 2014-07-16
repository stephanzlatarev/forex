FROM dockerfile/java

MAINTAINER Stephan Zlatarev

# Install Maven and Git
RUN apt-get install maven -y

# Fetch the application and package it into an executable jar
RUN git clone https://github.com/stephanzlatarev/forex.git /root/forex
WORKDIR /root/forex
RUN mvn package

# Prepare docker container for start up
EXPOSE 8080
# ENTRYPOINT java -jar target/forex-1.0.0.jar
