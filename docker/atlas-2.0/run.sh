#https://hub.docker.com/r/sburn/apache-atlas

#Start Atlas exposing logs directory on the host to view them directly:
#Start Atlas exposing conf directory on the host to place and edit configuration files directly:
#Start Atlas with data directory mounted on the host to provide its persistency:
#docker run -d --privileged=true \

docker run -d --privileged=true \
    -v ~/data/docker-atlas/data:/opt/apache-atlas-2.1.0/data \
    -v ~/data/docker-atlas/logs:/opt/apache-atlas-2.1.0/logs \
    -v ~/data/docker-atlas/conf:/opt/apache-atlas-2.1.0/conf \
    -p 21000:21000 \
    --name atlas \
    sburn/apache-atlas \
    /opt/apache-atlas-2.1.0/bin/atlas_start.py
