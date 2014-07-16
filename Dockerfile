FROM dockerfile/java

MAINTAINER Stephan Zlatarev

# Install Maven and Git
RUN apt-get install maven -y

# Fetch the application and package it
RUN git clone https://github.com/stephanzlatarev/forex.git /root/forex
WORKDIR /root/forex
RUN mvn package

# Prepare docker container for start up
EXPOSE 8080
RUN chmod +x ./go.sh
ENTRYPOINT ./go.sh
