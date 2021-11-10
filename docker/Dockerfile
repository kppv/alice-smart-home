FROM openjdk:11-jdk-slim
COPY ./target /usr/src/alice-smart-home
WORKDIR /usr/src/alice-smart-home
CMD java -jar -Dspring.profiles.active=docker alice-smart-home.jar
