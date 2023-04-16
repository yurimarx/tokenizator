FROM openjdk:17-jdk-slim
LABEL author="Yuri Gomes"
LABEL compaty="ymservices.tech"
COPY src/main/resources/tokenizator-1.0.0.jar tokenizator-1.0.0.jar
ENTRYPOINT ["java","-jar","/tokenizator-1.0.0.jar"]