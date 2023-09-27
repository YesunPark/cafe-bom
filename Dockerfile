FROM openjdk:11-jre-slim
COPY build/libs/CafeBom-1.0.2.jar CafeBom-1.0.2.jar

ENTRYPOINT ["java", "-DSpring.profiles.active=prod", "-jar" ,"CafeBom-1.0.2.jar"]