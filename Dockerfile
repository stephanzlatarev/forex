FROM dockerfile/java

MAINTAINER Stephan Zlatarev

# Install Maven and Git
RUN apt-get install maven -y

# Fetch the application
RUN git clone https://github.com/stephanzlatarev/forex.git /root/forex

# Prepare docker container for start up
EXPOSE 8080
RUN chmod +x /root/forex/go.sh
ENTRYPOINT /root/forex/go.sh
