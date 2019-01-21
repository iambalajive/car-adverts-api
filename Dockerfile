FROM openjdk:8-jre-alpine
RUN java -version
WORKDIR /
EXPOSE 8000 8001
RUN echo -e "http://nl.alpinelinux.org/alpine/v3.5/main\nhttp://nl.alpinelinux.org/alpine/v3.5/community" > /etc/apk/repositories
RUN apk update && apk add bash
COPY config/config.yml /config.yml
COPY target/universal/car-adverts-*.zip /car-adverts.zip
RUN unzip car-adverts.zip
ENTRYPOINT ["./car-adverts-api-0.1/bin/car-adverts-api","server","./config.yml"]