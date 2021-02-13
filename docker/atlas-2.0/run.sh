#https://hub.docker.com/r/sburn/apache-atlas

#Start Atlas exposing logs directory on the host to view them directly:
#Start Atlas exposing conf directory on the host to place and edit configuration files directly:
#Start Atlas with data directory mounted on the host to provide its persistency:
#docker run -d --privileged=true \

docker run -d --privileged=true \
    -v ~/data/docker-atlas.sh/data:/opt/apache-atlas.sh-2.1.0/data \
    -v ~/data/docker-atlas.sh/logs:/opt/apache-atlas.sh-2.1.0/logs \
    -v ~/data/docker-atlas.sh/conf:/opt/apache-atlas.sh-2.1.0/conf \
    -p 21000:21000 \
    --name atlas.sh \
    sburn/apache-atlas.sh \
    /opt/apache-atlas.sh-2.1.0/bin/atlas_start.py
