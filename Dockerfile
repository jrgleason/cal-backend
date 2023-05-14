# syntax=docker/dockerfile:1

FROM sbtscala/scala-sbt:eclipse-temurin-17.0.4_1.7.1_3.2.0
WORKDIR /app
COPY . /app
ENV SPRING_PROFILES_ACTIVE=private-docker
CMD sbt \~run