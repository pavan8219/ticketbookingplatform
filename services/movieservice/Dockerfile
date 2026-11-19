FROM eclipse-temurin:21-jre

WORKDIR /home/app

COPY target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]