FROM dockerfile/java

MAINTAINER Stephan Zlatarev

# Install Maven and Git
RUN apt-get install maven -y

# Fetch the spring boot application and package it
RUN git clone --no-checkout https://github.com/stephanzlatarev/forex.git /data && git checkout master
RUN ls -l

# Prepare docker container for start up
EXPOSE 8080
RUN chmod 777 go.sh
ENTRYPOINT /data/go.sh
