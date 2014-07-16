forex
=====

###
#
# How to run the application with https://cloud.digitalocean.com

# Build and publish the docker container, e.g. https://registry.hub.docker.com/u/stephanzlatarev/forex/builds_history/33603/

# Create a droplet of type Docker, https://cloud.digitalocean.com/droplets/new
# Enter "forex" as hostname
# Open a console to it, e.g. https://cloud.digitalocean.com/droplets/2101699/console

# Once the console, edit UFW configuration:
#   root@forex:~# sudo nano /etc/default/ufw
# Replace:
#   DEFAULT_FORWARD_POLICY="ACCEPT"
#   CTRL+X and approve with Y to save and close
# Reload the UFW:
#   root@forex:~# sudo ufw reload

# Pull the image:
#   root@forex:~# docker pull stephanzlatarev/forex

# Start the application:
#   root@forex:~# docker run -i -t -p 8080  -e "forex.username=krastavitcharyat@yahoo.com" -e "forex.password=s...ks" stephanzlatarev/forex

# Exit to the console:
#   Ctrl+P Ctrl+Q

# Check the published port
#   root@forex:~# docker ps -a

# Access the application via Web browser:
#   http://<droplet-ip-address>:<docker-published-port>
