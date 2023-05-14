FROM openjdk:11-buster
COPY ./target/alkobot-app.jar /opt/alkobot/alkobot-app.jar
WORKDIR /opt/alkobot

CMD java -jar alkobot-app.jar