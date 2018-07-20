FROM openjdk:8-jre-alpine
LABEL author="David Armbrust"
LABEL version="0.1"
COPY build/libs/playlist-server-0.1.9.jar opt/playlist-server-latest.jar
COPY build/libs/playlist-server-0.1.9.jar opt/playlist-server-latest.jar
COPY secure.properties opt/
COPY application.properties opt/
WORKDIR /opt/
CMD ["java", "-jar", "playlist-server-latest.jar"]
EXPOSE 8082