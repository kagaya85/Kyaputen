FROM openjdk:8u252-jdk
COPY ./client.jar /java/
COPY ./clientConfig.json /java/
WORKDIR /java
EXPOSE 80 11454
CMD ["java", "-jar", "client.jar"]