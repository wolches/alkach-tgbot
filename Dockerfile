FROM openjdk:11-jre-slim
COPY ./target/alkobot-app.jar /opt/alkobot
RUN java -jar "/opt/alkobot/alkobot-app.jar"