FROM openjdk:11-jre-slim
ENV TZ=Asia/Seoul
RUN mkdir -p /rolls/log /rolls/excel /rolls/static/fonts
WORKDIR /rolls
COPY ./build/libs/*.jar ./app.jar
ENTRYPOINT ["java","-jar","./app.jar"]