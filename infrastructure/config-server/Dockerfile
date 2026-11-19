FROM eclipse-temurin:21-jre

WORKDIR /home/app

COPY target/*.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "app.jar"]