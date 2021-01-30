#https://github.com/Zenika/alpine-chrome/blob/master/Dockerfile
FROM alpine:latest
MAINTAINER haijun.zhang
ARG BUILD_DATE
ARG VCS_REF

LABEL org.label-schema.build-date=$BUILD_DATE \
    org.label-schema.description="Chrome running in headless mode in a tiny Alpine image" \
    org.label-schema.name="alpine-chrome" \
    org.label-schema.schema-version="1.0.0-rc1" \
    org.label-schema.usage="https://github.com/Zenika/alpine-chrome/blob/master/README.md" \
    org.label-schema.vcs-url="https://github.com/Zenika/alpine-chrome" \
    org.label-schema.vcs-ref=$VCS_REF \
    org.label-schema.vendor="Zenika" \
    org.label-schema.version="latest"

# Installs latest Chromium package.
RUN echo "http://dl-cdn.alpinelinux.org/alpine/edge/main" > /etc/apk/repositories \
    && echo "http://dl-cdn.alpinelinux.org/alpine/edge/community" >> /etc/apk/repositories \
    && echo "http://dl-cdn.alpinelinux.org/alpine/edge/testing" >> /etc/apk/repositories \
    && echo "http://dl-cdn.alpinelinux.org/alpine/v3.12/main" >> /etc/apk/repositories \
    && apk upgrade -U -a \
    && apk add \
    libstdc++ \
    chromium \
    chromium-chromedriver \
    harfbuzz \
    nss \
    freetype \
    ttf-freefont \
    font-noto-emoji \
    wqy-zenhei \
    && rm -rf /var/cache/* \
    && mkdir /var/cache/apk

#COPY local.conf /etc/fonts/local.conf

ENV CHROME_BIN=/usr/bin/chromium-browser \
    CHROME_PATH=/usr/lib/chromium/ \
    CHROMEDRIVER_PATH=/usr/bin/chromedriver

RUN apk add openjdk8 curl busybox tzdata \
 && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
 && echo Asia/Shanghai > /etc/timezone \
 && apk del tzdata \
 && rm -rf /tmp/* /var/cache/apk/*
# Autorun chrome headless
#ENTRYPOINT ["chromium-browser", "--headless", "--use-gl=swiftshader", "--disable-software-rasterizer", "--disable-dev-shm-usage"]
