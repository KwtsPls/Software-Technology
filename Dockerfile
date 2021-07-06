FROM gradle:7.0.2-jdk11
VOLUME /tmp

ADD cli-app/src src
ADD back-end ../back-end
ADD cli-app/build.gradle ./
ADD cli-app/settings.gradle ./
RUN gradle clean

RUN gradle build


FROM openjdk:11

COPY cli-app/build/libs/cli-app-0.0.1-SNAPSHOT.jar cliapp.jar


ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cliapp.jar"]
