FROM openjdk:8-jre-alpine
RUN java -version
WORKDIR /
EXPOSE 8000 8001
COPY config/config.yml /config.yml
COPY target/universal/car-adverts-*.zip /car-adverts.zip
RUN apk update && apk add bash
RUN unzip car-adverts.zip
ENTRYPOINT ["./car-adverts-api-0.1/bin/car-adverts-api","server","./config.yml"]