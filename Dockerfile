FROM dockerfile/java

MAINTAINER Stephan Zlatarev

# Install Maven and Git
# RUN apt-get install maven -y

# Fetch the application
RUN git clone https://github.com/stephanzlatarev/forex.git /root/forex
RUN cp -rf /root/forex /data
RUN ls -l
RUN chmod +x /root/forex/go.sh

# Prepare docker container for start up
EXPOSE 8080
# ENTRYPOINT /data/go.sh
