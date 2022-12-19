FROM openjdk:11-buster
COPY ./target/alkobot-app.jar /opt/alkobot/alkobot-app.jar
ENTRYPOINT ["java", "-jar", "/opt/alkobot/alkobot-app.jar"]