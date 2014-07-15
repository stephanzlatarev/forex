FROM dockerfile/java

MAINTAINER Stephan Zlatarev

# Install Maven and Git
RUN apt-get install maven -y

# Fetch the spring boot application and package it (https://github.com/stephanzlatarev/forex.gits)
RUN git clone git@github.com:stephanzlatarev/forex.git
RUN pwd
RUN ls -l

# Prepare docker container for start up
EXPOSE 8080
RUN chmod 777 forex/go.sh
ENTRYPOINT forex/go.sh
