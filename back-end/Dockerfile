FROM gradle:7.0.2-jdk11
VOLUME /tmp

ADD src src
ADD build.gradle ./
ADD settings.gradle ./
ADD src/main/resources/application.properties ./
RUN gradle clean

RUN gradle build


FROM openjdk:11

COPY build/libs/jete-0.0.1-SNAPSHOT.jar jeteapp.jar


ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/jeteapp.jar"]
