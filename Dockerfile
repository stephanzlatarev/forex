FROM phusion/baseimage

MAINTAINER Stephan Zlatarev

# Install Maven and Git
RUN apt-get install git -y
RUN apt-get install maven -y

# Install Java 7 accepting licensing and terms of use
RUN apt-get install software-properties-common -y
RUN apt-add-repository ppa:webupd8team/java -y
RUN apt-get update
RUN echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
RUN apt-get install oracle-java7-installer -y

# Fetch the spring boot application and package it
RUN git clone https://github.com/stephanzlatarev/forex.git
RUN cd forex
RUN mvn package

# Prepare docker container for start up
EXPOSE 8080
RUN chmod 777 go.sh
ENTRYPOINT forex/go.sh
